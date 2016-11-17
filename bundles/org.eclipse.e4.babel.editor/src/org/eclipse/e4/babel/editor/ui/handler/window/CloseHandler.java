package org.eclipse.e4.babel.editor.ui.handler.window;

import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class CloseHandler {
    private static final String TAG = CloseHandler.class.getSimpleName();

    @Execute
    public void execute(final IWorkbench workbench, final EPartService partService, final IEclipseContext context) {
	MPart part = partService.getActivePart();
	if (part.getObject() instanceof ResourceBundleEditorContract.View) {
	    part.setDirty(false);
	    partService.hidePart(part, true);

	}
    }

    @CanExecute
    public boolean canExecute(@Optional EPartService partService) {
	boolean isVisible = false;
	if (partService != null) {
	    MPart activePart = partService.getActivePart();
	    if (activePart.getObject() instanceof ResourceBundleEditorContract.View) {
		isVisible = true;
	    }
	}
	return isVisible;
    }
}
