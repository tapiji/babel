package org.eclipse.e4.babel.editor.ui.editor.composite;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public final class I18nComposite extends ScrolledComposite {

    private final List<BundleEntryComposite> bundleEntryComposite = new ArrayList<>();

    public static I18nComposite create(final Composite sashForm) {
        return new I18nComposite(sashForm, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private I18nComposite(final Composite sashForm, final int style) {
        super(sashForm, style);


        final Composite comp = new Composite(this, SWT.BORDER);
        comp.setLayout(new GridLayout(1, false));


        for (int i = 0; i < 30; i++) {
            bundleEntryComposite.add(BundleEntryComposite.create(comp, this));
        }
        this.setContent(comp);
        this.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);
        this.setShowFocusedControl(true);
    }
}
