package org.eclipse.e4.babel.editor.ui.editor.tree.handler;

import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public final class CollapseAllHandler {

    @Execute
    public void execute(final MPart part) {
	if (part.getObject() instanceof ResourceBundleEditorContract.View) {
	    ResourceBundleEditorContract.View resourceBundleEditorContract = (ResourceBundleEditorContract.View) part.getObject();
	    resourceBundleEditorContract.getKeyTreeView().collapseAll();
	}
    }
}
