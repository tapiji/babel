package org.eclipse.e4.babel.editor.ui.handler.window;

import javax.inject.Named;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.utils.FileUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipselabs.e4.tapiji.logger.Log;

public class OpenResourceBundleHandler {

	private static final String TAG = OpenResourceBundleHandler.class.getSimpleName();
	private static final String PART_STACK_ID = "org.eclipse.e4.babel.editor.partstack.editorPartStack";
	private static final String PART_ID = "org.eclipse.e4.babel.editor.partdescriptor.resourceBundleEditor";
	public static String KEY_FILE = OpenResourceBundleHandler.class.getSimpleName() + "_KEY_FILE";

	@Execute
	public void execute(final MApplication application, final IWorkbench workbench,
			@Named(IServiceConstants.ACTIVE_SHELL) final Shell shell, final EPartService partService,
			final EModelService modelService, IEclipseContext eclipseContext) {

		final String[] fileNames = FileUtils.queryFileName(shell, "Open Resource-Bundle", SWT.OPEN,
				FileUtils.PROPERTY_FILE_ENDINGS);
		if (fileNames != null) {
			final String fileName = fileNames[0];
			if (!FileUtils.isResourceBundle(fileName)) {
				MessageDialog.openError(shell, String.format("Cannot open Resource-Bundle %s", fileName),
						"The choosen file does not represent a Resource-Bundle!");
				return;
			}


			try {
				final IFile file = FileUtils.getResourceBundleRef(fileName, FileUtils.EXTERNAL_RB_PROJECT_NAME);
				if (file != null) {
					MPartStack mainStack = (MPartStack) modelService.find(PART_STACK_ID, application);
					MPart part = partService.createPart(PART_ID);
					part.getTransientData().put(KEY_FILE, file);
					mainStack.getChildren().add(part);
					partService.showPart(part, PartState.ACTIVATE);

				} else {
					Log.d(TAG, "File input is null!");
				}
			} catch (CoreException exception) {
				Log.e(TAG, exception);
			}
		}
	}
}
