package org.eclipse.e4.babel.editor.ui.handler.window;


import javax.inject.Named;
import org.eclipse.e4.babel.editor.preference.PreferenceDialogPage;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;


public class ShowPreferenceHandler {

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SHELL) final Shell shell) {
        PreferenceManager mgr = new PreferenceManager();
        PreferenceDialog dlg = new PreferenceDialog(shell, mgr);

        IPreferenceNode one = new PreferenceNode("one", new PreferenceDialogPage());

        IPreferenceNode two = new PreferenceNode("two", new PreferenceDialogPage());
        mgr.addToRoot(one);
        mgr.addTo("one", two);
        dlg.create();


        dlg.open();
    }


}
