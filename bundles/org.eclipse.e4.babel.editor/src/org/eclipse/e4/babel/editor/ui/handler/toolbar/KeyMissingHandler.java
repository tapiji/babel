package org.eclipse.e4.babel.editor.ui.handler.toolbar;

import org.eclipse.e4.babel.editor.model.tree.filter.MissingKeyFilter;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipselabs.e4.tapiji.logger.Log;

public final class KeyMissingHandler {

    private static final String TAG = KeyMissingHandler.class.getSimpleName();

    @Execute
    public void execute(final MPart part) {
	Log.d(TAG, "execute sadasd");
	if (part.getObject() instanceof ResourceBundleEditorContract.View) {
	    ResourceBundleEditorContract.View resourceBundleEditor = (ResourceBundleEditorContract.View) part.getObject();
	    resourceBundleEditor.getResourceManager().setTreeFilter(new MissingKeyFilter());
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
