package org.eclipse.e4.babel.editor.ui.handler.toolbar;

import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipselabs.e4.tapiji.logger.Log;

public final class KeyHandler {

    private static final String TAG = KeyHandler.class.getSimpleName();

    @Execute
    public void execute(MPart part) {
	Log.d(TAG, "execute sdsdsd");
	if (part.getObject() instanceof ResourceBundleEditorContract.View) {
	    ResourceBundleEditorContract.View resourceBundleEditor = (ResourceBundleEditorContract.View) part.getObject();
	    resourceBundleEditor.getResourceManager().setTreeFilter(null);
	    resourceBundleEditor.getKeyTreeView().getTreeViewer().getControl().setRedraw(false);
	    resourceBundleEditor.getKeyTreeView().getTreeViewer().refresh();
	    resourceBundleEditor.getKeyTreeView().getTreeViewer().getControl().setRedraw(true);
	}
    }

    @CanExecute
    public boolean canExecute() {
	return true;
    }
}
