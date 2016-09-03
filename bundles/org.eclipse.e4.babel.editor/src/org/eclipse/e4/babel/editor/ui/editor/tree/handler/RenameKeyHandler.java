package org.eclipse.e4.babel.editor.ui.editor.tree.handler;

import javax.inject.Named;

import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public final class RenameKeyHandler { // NO_UCD

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) KeyTreeItem item, @Named(IServiceConstants.ACTIVE_SHELL) final Shell shell, MPart part) {
	if (part.getObject() instanceof ResourceBundleEditorContract.View) {
	    ResourceBundleEditorContract.View resourceBundleEditorContract = (ResourceBundleEditorContract.View) part.getObject();
	    String key = item.getId();
	    String msgHead = null;
	    String msgBody = null;
	    if (item.getChildren().size() == 0) {
		msgHead = "Rename key";
		msgBody = "Rename " + key + " to:";
	    } else {
		msgHead = "Rename key group";
		msgBody = "Rename key grou p" + key + " to (all nested keys will be renamed):";
	    }

	    InputDialog dialog = new InputDialog(shell, msgHead, msgBody, key, null);
	    dialog.setErrorMessage("sddadasd");

	    dialog.open();

	    if (dialog.getReturnCode() == Window.OK) {
		dialog.getValue();
		resourceBundleEditorContract.getResourceManager().renameKey(item, dialog.getValue());
	    }
	}
    }
}
