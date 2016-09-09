package org.eclipse.e4.babel.editor.ui.handler.window;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;

import org.eclipse.e4.babel.core.utils.BabelUtils;
import org.eclipse.e4.babel.editor.text.file.PropertyFileResource;
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

public class OpenResourceBundleHandler {

    private static final String TAG = OpenResourceBundleHandler.class.getSimpleName();

    private static final String PART_STACK_ID = "org.eclipse.e4.babel.editor.partstack.editorPartStack";
    private static final String PART_ID = "org.eclipse.e4.babel.editor.partdescriptor.resourceBundleEditor";

    public static String KEY_FILE_DOCUMENT = OpenResourceBundleHandler.class.getSimpleName() + "_KEY_FILE_DOCUMENT";

    @Execute
    public void execute(final MApplication application, final IWorkbench workbench, @Named(IServiceConstants.ACTIVE_SHELL) final Shell shell, final EPartService partService,
	    final EModelService modelService, IEclipseContext eclipseContext) {
	final String[] fileNames = BabelUtils.queryFileName(shell, "Open Resource-Bundle", SWT.OPEN, BabelUtils.PROPERTY_FILE_ENDINGS);
	if (fileNames != null) {
	    final String fileName = fileNames[0];
	    if (!BabelUtils.isResourceBundle(fileName)) {
		MessageDialog.openError(shell, String.format("Cannot open Resource-Bundle %s", fileName), "The choosen file does not represent a Resource-Bundle!");
		return;
	    }

	    // part.getTransientData().put(KEY_FILE_DOCUMENT,
	    // IFileResource.create(filei));
	    MPart part = partService.createPart(PART_ID);
	    try {
		part.getTransientData().put(KEY_FILE_DOCUMENT, PropertyFileResource.create(new File(fileName)));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    MPartStack mainStack = (MPartStack) modelService.find(PART_STACK_ID, application);
	    mainStack.getChildren().add(part);
	    partService.showPart(part, PartState.ACTIVATE);

	}
    }
}
