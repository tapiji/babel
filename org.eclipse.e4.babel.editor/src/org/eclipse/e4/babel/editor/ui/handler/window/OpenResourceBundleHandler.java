package org.eclipse.e4.babel.editor.ui.handler.window;


import java.util.Random;
import javax.inject.Named;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MCompositePart;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.eclipselabs.e4.tapiji.utils.FileUtils;


public class OpenResourceBundleHandler {

    private static final String TAG = OpenResourceBundleHandler.class.getSimpleName();

    @Execute
    public void execute(final MApplication application, final IWorkbench workbench, @Named(IServiceConstants.ACTIVE_SHELL) final Shell shell, final EPartService partService,
                    final EModelService modelService) {

        final String[] fileNames = FileUtils.queryFileName(shell, "Open Resource-Bundle", SWT.OPEN, FileUtils.PROPERTY_FILE_ENDINGS);
        if (fileNames != null) {
            final String fileName = fileNames[0];
            if (!FileUtils.isResourceBundle(fileName)) {
                MessageDialog.openError(shell, String.format("Cannot open Resource-Bundle %s", fileName), "The choosen file does not represent a Resource-Bundle!");
                return;
            }
            Log.d(TAG, "FileName: " + fileName);

            IFile file;
            try {
                file = FileUtils.getResourceBundleRef(fileName, FileUtils.EXTERNAL_RB_PROJECT_NAME);
                final IEditorInput input = new FileEditorInput(file);

                MPartStack mainStack = (MPartStack) modelService.find("org.eclipse.e4.babel.editor.partstack.editorPartStack", application);

                MCompositePart compositePart = MBasicFactory.INSTANCE.createCompositePart();
                MPartStack newStack = MBasicFactory.INSTANCE.createPartStack();

                compositePart.getChildren().add(newStack);


                mainStack.getChildren().add(compositePart);


                //     MPartStack newStack = modelService.createModelElement(MPartStack.class);
                newStack.setElementId("test" + new Random().nextInt(52));
                newStack.getPersistedState().put("styleOverride", "1024");
                compositePart.getChildren().add(newStack);

                //     stack.Log.d(TAG, "Stack: " + stack.toString());

                MPart part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.resourceBundleEditor");
                part.getTransientData().put("FILE", input);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);

                part = partService.createPart("org.eclipse.e4.babel.editor.partdescriptor.test");
                newStack.getChildren().add(part);


                Log.d(TAG, "Stack: " + part);

                partService.showPart(part, PartState.ACTIVATE);
            } catch (CoreException exception) {
                Log.e(TAG, exception);
            }

        }


    }
}
