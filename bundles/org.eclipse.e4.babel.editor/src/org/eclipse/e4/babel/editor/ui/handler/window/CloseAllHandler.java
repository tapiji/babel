package org.eclipse.e4.babel.editor.ui.handler.window;

import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class CloseAllHandler {
    private static final String TAG = CloseAllHandler.class.getSimpleName();

    @Execute
    public void execute(final IWorkbench workbench, final EPartService partService, final IEclipseContext context) {
	partService.getParts().forEach(part -> {
	    if (part.getObject() instanceof ResourceBundleEditorContract.View) {
		partService.hidePart(part, true);
		part.setDirty(false);
	    }
	});
    }

    @CanExecute
    public boolean canExecute(@Optional final EPartService partService) {
	boolean partAvailale = false;
	if (partService != null) {
	    MPart foundPart = partService.getParts().stream().filter(part -> part instanceof ResourceBundleEditorContract.View).findFirst().orElse(null);
	    if (foundPart != null) {
		partAvailale = true;
	    }
	}
	return partAvailale;
    }
}
