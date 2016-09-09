package org.eclipse.e4.babel.editor.ui.editor.tree.handler;

import javax.inject.Named;

import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public final class DeleteKeyHandler {// NO_UCD

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) KeyTreeItem item, @Named(IServiceConstants.ACTIVE_SHELL) final Shell shell, MPart part) {
	if (part.getObject() instanceof ResourceBundleEditorContract.View) {
	    ResourceBundleEditorContract.View resourceBundleEditorContract = (ResourceBundleEditorContract.View) part.getObject();
	    if (item != null) {
		String msgHead = null;
		String msgBody = null;
		if (item.getChildren().size() == 0) {
		    msgHead = "Delete key";
		    msgBody = "Are you sure you want to delete " + item.getId() + " ?";
		} else {
		    msgHead = "Delete key group";
		    msgBody = "Are you sure you want to delete all keys in " + item.getName() + " group?";
		}
		MessageBox msgBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
		msgBox.setMessage(msgBody);
		msgBox.setText(msgHead);
		if (msgBox.open() == SWT.OK) {
		    resourceBundleEditorContract.getResourceManager().removeKey(item, item.getId());
		}
	    }
	}
    }
}
