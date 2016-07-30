package org.eclipse.e4.babel.editor.ui.handler.toolbar;

import org.eclipse.e4.babel.editor.model.updater.IncompletionUpdater;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.logger.Log;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public final class KeyMissingHandler {

    private static final String TAG = KeyMissingHandler.class.getSimpleName();

    @Execute
    public void execute(final MPart part) {
	Log.d(TAG, "execute sadasd");
	if (part.getObject() instanceof ResourceBundleEditorContract.View) {
	    ResourceBundleEditorContract.View resourceBundleEditor = (ResourceBundleEditorContract.View) part.getObject();
	    KeyTreeUpdater updater = resourceBundleEditor.getKeyTreeView().getPresenter().getKeyTreeUpdater();
	    resourceBundleEditor.getKeyTreeView().getKeyTree().setUpdater(new IncompletionUpdater(resourceBundleEditor.getKeyTreeView().getKeyTree().getBundleGroup(), updater));
	}
    }

    @CanExecute
    public boolean canExecute() {
	return true;
    }
}
