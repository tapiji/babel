package org.eclipse.e4.babel.editor.ui.editor.composite;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipselabs.e4.tapiji.resource.ITapijiResourceProvider;


public final class I18nComposite extends ScrolledComposite implements BundleEntryComposite.IBundleEntryComposite {

    private static final String TAG = I18nComposite.class.getSimpleName();
    private final List<BundleEntryComposite> bundleEntries = new ArrayList<>();

    private BundleEntryComposite activeBundleEntry;

    public static I18nComposite create(final Composite sashForm, final ITapijiResourceProvider resourceProvider) {
        return new I18nComposite(sashForm, resourceProvider, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private I18nComposite(final Composite sashForm, final ITapijiResourceProvider resourceProvider, final int style) {
        super(sashForm, style);


        final Composite comp = new Composite(this, SWT.BORDER);
        comp.setLayout(new GridLayout(1, false));

        for (int i = 0; i < 8; i++) {
            final BundleEntryComposite entry = BundleEntryComposite.create(comp, resourceProvider);
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

    private void setFocusForNextComposite(final BundleEntryComposite nextFocusComposite) {
        nextFocusComposite.setFocusTextView();
        setOrigin(getOrigin().x, nextFocusComposite.getLocation().y);
    }

    @Override
    public void onFocusChange(final BundleEntryComposite bundleEntry) {
        activeBundleEntry = bundleEntry;
    }
}
