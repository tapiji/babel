package org.eclipse.e4.babel.editor.ui.editor.composite;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class I18nComposite extends ScrolledComposite implements BundleEntryComposite.IBundleEntryComposite {

    private static final String TAG = I18nComposite.class.getSimpleName();
    private final List<BundleEntryComposite> bundleEntryComposite = new ArrayList<>();
    private BundleEntryComposite activeBundleEntryComposite;

    public static I18nComposite create(final Composite sashForm) {
        return new I18nComposite(sashForm, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private I18nComposite(final Composite sashForm, final int style) {
        super(sashForm, style);


        final Composite comp = new Composite(this, SWT.BORDER);
        comp.setLayout(new GridLayout(1, false));


        for (int i = 0; i < 2; i++) {
            final BundleEntryComposite entry = BundleEntryComposite.create(comp);
            entry.addListener(this);
            bundleEntryComposite.add(BundleEntryComposite.create(comp));
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
        Log.d(TAG, "NextFocusDown");
        if (null != activeBundleEntryComposite) {
            final int index = bundleEntryComposite.indexOf(activeBundleEntryComposite);
            Log.d(TAG, "INDEX: " + index);
        }
    }

    @Override
    public void setNextFocusUp() {

    }

    @Override
    public void onFocusChange(BundleEntryComposite bundleEntryComposite) {
        activeBundleEntryComposite = bundleEntryComposite;
    }
}
