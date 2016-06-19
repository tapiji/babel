package org.eclipse.e4.babel.editor.ui.handler.toolbar;


import javax.inject.Named;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class KeyUnusedHandler {

    private static final String TAG = KeyUnusedHandler.class.getSimpleName();

    @Execute
    public void execute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) final KeyTreeItem term) {
        Log.d(TAG, "execute"+term.getName());
    }
}
