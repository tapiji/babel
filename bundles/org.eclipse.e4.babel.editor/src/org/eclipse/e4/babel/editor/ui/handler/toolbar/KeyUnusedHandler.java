package org.eclipse.e4.babel.editor.ui.handler.toolbar;


import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class KeyUnusedHandler {

    private static final String TAG = KeyUnusedHandler.class.getSimpleName();

    @Execute
    public void execute(final MPart part) {
        Log.d(TAG, "execute"+part);
    }
}
