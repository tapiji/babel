package org.eclipse.e4.babel.editor.ui.handler.toolbar;


import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditor;
import org.eclipse.e4.babel.logger.Log;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;


public final class KeyTreeVisibleHandler {

    private static final String TAG = KeyTreeVisibleHandler.class.getSimpleName();

    @Execute
    public void execute(final IEventBroker eventBroker) {
        Log.d(TAG, "execute");
        eventBroker.post(ResourceBundleEditor.TOPIC_TREE_VIEW_VISIBILITY,false);
    }
    
    @CanExecute
    public boolean canExecute() {
        return true;
    }
}
