package org.eclipse.e4.babel.editor.ui.editor.composite;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public final class I18nPage extends ScrolledComposite implements BundleEntry.IBundleEntryComposite {

    private static final String TAG = I18nPage.class.getSimpleName();
    private final List<BundleEntry> bundleEntries = new ArrayList<>();

    private BundleEntry activeBundleEntry;

    public static I18nPage create(final Composite sashForm, final IBabelResourceProvider resourceProvider) {
        return new I18nPage(sashForm, resourceProvider, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private I18nPage(final Composite sashForm, final IBabelResourceProvider resourceProvider, final int style) {
        super(sashForm, style);


        final Composite comp = new Composite(this, SWT.BORDER);
        comp.setLayout(new GridLayout(1, false));

        for (int i = 0; i < 8; i++) {
            final BundleEntry entry = BundleEntry.create(comp, resourceProvider);
            entry.addListener(this);
            bundleEntries.add(entry);
        }
        this.setContent(comp);
        this.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);
        this.setShowFocusedControl(true);
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

    private void setFocusForNextComposite(final BundleEntry nextFocusComposite) {
        nextFocusComposite.setFocusTextView();
        setOrigin(getOrigin().x, nextFocusComposite.getLocation().y);
    }

    @Override
    public void onFocusChange(final BundleEntry bundleEntry) {
        activeBundleEntry = bundleEntry;
    }
}
