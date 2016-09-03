package org.eclipse.e4.babel.editor.ui.editor.tree.handler;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.inject.Named;

import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;

public class CopyKeyHandler {
    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) KeyTreeItem item) {
	if (item != null) {
	    StringSelection selectKey = new StringSelection(item.getId());
	    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clip.setContents(selectKey, selectKey);
	}
    }
}
