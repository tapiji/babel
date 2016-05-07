package org.eclipse.e4.babel.editor.ui.handler.window;


import javax.inject.Named;
import org.eclipse.e4.babel.editor.preference.PreferenceFormattingPage;
import org.eclipse.e4.babel.editor.preference.PreferenceGeneralPage;
import org.eclipse.e4.babel.editor.preference.PreferencePerformancePage;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;


public final class ShowPreferenceHandler {
    
    public static final String TOPIC_REFRESH_LAYOUT = "TOPIC_GUI/REFRESH_LAYOUT";
    private static final String PAGE_GENERAL = "page_general";
    private static final String PAGE_FORMATTING = "page_formatting";
    private static final String PAGE_REPORTING = "page_reporting";
    private IEventBroker eventBroker;

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SHELL) final Shell shell, final IEventBroker eventBroker) {
        this.eventBroker = eventBroker;
        final PreferenceManager mgr = new PreferenceManager();
        final PreferenceDialog dlg = new PreferenceDialog(shell, mgr);

        final IPreferenceNode generalPage = new PreferenceNode(PAGE_GENERAL, new PreferenceGeneralPage(this));
        final IPreferenceNode formattingPage = new PreferenceNode(PAGE_FORMATTING, new PreferenceFormattingPage(this));
        final IPreferenceNode reportingPage = new PreferenceNode(PAGE_REPORTING, new PreferencePerformancePage(this));
        mgr.addToRoot(generalPage);
        mgr.addTo(PAGE_GENERAL, formattingPage);
        mgr.addTo(PAGE_GENERAL, reportingPage);
        dlg.create();
        dlg.open();
    }
    
    public void refreshLayout() {
        eventBroker.post(TOPIC_REFRESH_LAYOUT, "refresh");
    }
}
