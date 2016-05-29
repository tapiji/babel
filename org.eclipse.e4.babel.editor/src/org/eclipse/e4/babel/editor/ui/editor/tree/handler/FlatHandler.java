package org.eclipse.e4.babel.editor.ui.editor.tree.handler;


import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class FlatHandler {

    private static final String TAG = FlatHandler.class.getSimpleName();

    @Execute
    public void execute() {
        Log.d(TAG, "execute");
    }

    @CanExecute
    public boolean canExecute() {
        return true;
    }
}
