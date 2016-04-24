package org.eclipse.e4.babel.editor.ui.handler.toolbar;


import org.eclipse.e4.babel.editor.ui.editor.constant.EditorConstant;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class KeyTreeVisibleHandler {

    private static final String TAG = KeyTreeVisibleHandler.class.getSimpleName();

    @Execute
    public void execute(final IEventBroker eventBroker) {
        Log.d(TAG, "execute");
        eventBroker.post(EditorConstant.TOPIC_TREE_VIEW_VISIBILITY,false);
    }
    
    @CanExecute
    public boolean canExecute() {
        return true;
    }
}
