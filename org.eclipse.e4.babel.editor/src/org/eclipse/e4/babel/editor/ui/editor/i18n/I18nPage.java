package org.eclipse.e4.babel.editor.ui.editor.i18n;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public final class I18nPage extends ScrolledComposite implements I18nBundleEntry.IBundleEntryComposite {

    private static final String TAG = I18nPage.class.getSimpleName();
    private final List<I18nBundleEntry> bundleEntries = new ArrayList<>();

    private I18nBundleEntry activeBundleEntry;
    private Composite i18nEntryComposite;

    public static I18nPage create(final Composite sashForm, final IBabelResourceProvider resourceProvider) {
        return new I18nPage(sashForm, resourceProvider, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private I18nPage(final Composite sashForm, final IBabelResourceProvider resourceProvider, final int style) {
        super(sashForm, style);
        i18nEntryComposite = new Composite(this, SWT.BORDER);
        i18nEntryComposite.setLayout(new GridLayout(1, false));
        for (int i = 0; i < 8; i++) {
            final I18nBundleEntry entry = I18nBundleEntry.create(i18nEntryComposite,(ScrolledComposite)this, resourceProvider);
            entry.addListener(this);
            bundleEntries.add(entry);
        }
        this.setContent(i18nEntryComposite);
        this.setMinSize(i18nEntryComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);
        this.setShowFocusedControl(true);
    }
    
    public void refreshLayout() {
        this.setMinSize(i18nEntryComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT)); 
        this.layout(true, true);
    }

    @Override
    public void onLocaleClick() {

    }

    @Override
    public void setNextFocusDown() {
        if (null != activeBundleEntry) {
            final int index = bundleEntries.indexOf(activeBundleEntry);
            if ((index >= 0) && (index != (bundleEntries.size() - 1))) {
                setFocusForNextComposite(bundleEntries.get(index + 1));
            } else if (index == (bundleEntries.size() - 1)) {
                setFocusForNextComposite(bundleEntries.get(0));
            }
        }
    }

    @Override
    public void setNextFocusUp() {
        if (null != activeBundleEntry) {
            final int index = bundleEntries.indexOf(activeBundleEntry);
            if (index > 0) {
                setFocusForNextComposite(bundleEntries.get(index - 1));
            } else if (index == 0) {
                setFocusForNextComposite(bundleEntries.get(bundleEntries.size() - 1));
            }
        }
    }

    private void setFocusForNextComposite(final I18nBundleEntry nextFocusComposite) {
        nextFocusComposite.setFocusTextView();
        setOrigin(getOrigin().x, nextFocusComposite.getLocation().y);
    }

    @Override
    public void onFocusChange(final I18nBundleEntry bundleEntry) {
        activeBundleEntry = bundleEntry;
    }
}
