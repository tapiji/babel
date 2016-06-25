package org.eclipse.e4.babel.editor.ui.handler.toolbar;


import java.io.IOException;
import java.util.Locale;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.widget.LocaleSelector;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class NewLocaleHandler {

    private static final String TAG = NewLocaleHandler.class.getSimpleName();

    @Execute
    public void execute(final Shell shell, final MPart part) {
        final Dialog localeDialog = new Dialog(shell) {

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
            	
            	Locale newLocal = selector.getSelectedLocale();
            	
              	if (part.getObject() instanceof ResourceBundleEditorContract.View) {
        			ResourceBundleEditorContract.View resourceBundleEditor = (ResourceBundleEditorContract.View) part.getObject();
        			
        			if(!resourceBundleEditor.getResourceManager().getBundleGroup().containsLocale(newLocal)) {
        				try {
							IFile newFile = resourceBundleEditor.getResourceManager().createPropertiesFile(newLocal);
							Log.d(TAG, "CREATED FILE: "+newFile);
							Display.getDefault().asyncExec(new Runnable() {
	                            public void run() {
	                            	resourceBundleEditor.addResource(newFile, newLocal);
	                            }
	                        });
						} catch (CoreException | IOException e) {
							e.printStackTrace();
						}
        			};
              	}
                super.okPressed();
            }
        };
        // open dialog
        localeDialog.open();
    }

    @CanExecute
    public boolean canExecute() {
        return true;
    }
}
