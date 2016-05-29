package org.eclipse.e4.babel.editor.ui.editor.tree.handler;


import javax.inject.Named;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class RenameKeyHandler {

    private static final String TAG = RenameKeyHandler.class.getSimpleName();

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) KeyTreeItem item, @Named(IServiceConstants.ACTIVE_SHELL) final Shell shell) {
        String key = item.getId();
        String msgHead = null;
        String msgBody = null;
        if (item.getChildren()
                .size() == 0) {
            msgHead = "Rename key";
            msgBody = "Rename " + key + " to:";
        } else {
            msgHead = "Rename key group";
            msgBody = "Rename key grou p" + key + " to (all nested keys will be renamed):";
        }

        InputDialog dialog = new InputDialog(shell, msgHead, msgBody, key, null);
        dialog.open();
        if (dialog.getReturnCode() == Window.OK) {
            String newKey = dialog.getValue();
            Log.d(TAG, "Rename key to: " + newKey);
            // Todo add new key
        }
    }

    @CanExecute
    public boolean canExecute() {
        return true;
    }
}
