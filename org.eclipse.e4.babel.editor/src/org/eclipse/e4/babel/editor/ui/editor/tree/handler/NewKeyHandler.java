package org.eclipse.e4.babel.editor.ui.editor.tree.handler;


import javax.inject.Named;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class NewKeyHandler {

    private static final String TAG = NewKeyHandler.class.getSimpleName();

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) KeyTreeItem item, @Named(IServiceConstants.ACTIVE_SHELL) final Shell shell) {
        if (item != null) {
            System.out.println("sadadsd " + item.toString());
            InputDialog dialog = new InputDialog(shell, "Add new key","Below is the key that will be newly created:", item.getId(), null);
            dialog.open();
            if (dialog.getReturnCode() == Window.OK) {
                String newKey = dialog.getValue();
                Log.d(TAG, "New key to add: " + newKey);
                // Todo add new key
            }
        }
    }
}
