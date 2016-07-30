package org.eclipse.e4.babel.editor.ui.handler.toolbar;


import org.eclipse.e4.babel.logger.Log;
import org.eclipse.e4.core.di.annotations.Execute;


public final class KeyMissingUnusedHandler {

    private static final String TAG = KeyMissingUnusedHandler.class.getSimpleName();

    @Execute
    public void execute() {
        Log.d(TAG, "execute");
    }
}
