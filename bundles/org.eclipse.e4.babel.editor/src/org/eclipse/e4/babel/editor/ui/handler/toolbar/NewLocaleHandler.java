package org.eclipse.e4.babel.editor.ui.handler.toolbar;

import java.io.IOException;
import java.util.Locale;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.widget.LocaleSelector;
import org.eclipse.e4.babel.logger.Log;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public final class NewLocaleHandler {

    private static final String TAG = NewLocaleHandler.class.getSimpleName();
    private Dialog localeDialog;

    @Execute
    public void execute(final Shell shell, final MPart part) {
	localeDialog = new Dialog(shell) {

	    LocaleSelector selector;

	    @Override
	    protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Add new local");
	    }

	    @Override
	    protected Control createDialogArea(final Composite parent) {
		final Composite comp = (Composite) super.createDialogArea(parent);
		selector = new LocaleSelector(comp);
		return comp;
	    }

	    @Override
	    protected void okPressed() {
		final Locale selectedLocale = selector.getSelectedLocale();
		if (part.getObject() instanceof ResourceBundleEditorContract.View) {
		    ResourceBundleEditorContract.View resourceBundleEditor = (ResourceBundleEditorContract.View) part.getObject();
		    if (!resourceBundleEditor.getResourceManager().getBundleGroup().containsLocale(selectedLocale)) {
			try {
			    final IFile newFile = resourceBundleEditor.getResourceManager().createPropertiesFile(selectedLocale);
			    if (newFile != null) {
				//resourceBundleEditor.addResource(newFile, selectedLocale);
			    } else {
				if (showFileExistDialog()) {
				    localeDialog.open();
				}
			    }
			} catch (CoreException | IOException e) {
			    Log.e(TAG, e);
			}
		    }
		    ;
		}
		super.okPressed();
	    }
	};
	localeDialog.open();
    }

    private boolean showFileExistDialog() {
	return MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Sprache exestiert bereits", "Andere Sprache wählen?");
    }

    @CanExecute
    public boolean canExecute() {
	return true;
    }
}
