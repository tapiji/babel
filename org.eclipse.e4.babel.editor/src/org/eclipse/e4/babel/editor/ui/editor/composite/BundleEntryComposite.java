package org.eclipse.e4.babel.editor.ui.editor.composite;


import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public final class BundleEntryComposite extends ScrolledComposite {

    public static BundleEntryComposite create(final Composite sashForm) {
        return new BundleEntryComposite(sashForm, SWT.V_SCROLL | SWT.H_SCROLL);
    }

    private BundleEntryComposite(final Composite sashForm, final int style) {
        super(sashForm, style);

        final Composite comp = new Composite(this, SWT.NONE);
        comp.setLayout(new GridLayout(2, false));

        for (int i = 0; i < 10; i++) {
            final Composite composite = new Composite(comp, SWT.NONE);
            composite.setLayout(new GridLayout(2, false));

            final Label lblNewLabel = new Label(composite, SWT.NONE);
            lblNewLabel.setBounds(0, 0, 62, 15);
            lblNewLabel.setText("New Label" + i);

            final Label lblNewLabel_2 = new Label(composite, SWT.NONE);
            lblNewLabel_2.setText("New Label" + i);

            final Composite composite_1 = new Composite(comp, SWT.NONE);
            composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

            final Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
            lblNewLabel_1.setBounds(0, 0, 62, 15);
            lblNewLabel_1.setText("New Label" + i);

            final TextViewer textViewer = new TextViewer(comp, SWT.BORDER);
            final StyledText styledText = textViewer.getTextWidget();

            styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        }
        this.setContent(comp);
        this.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);
        this.setShowFocusedControl(true);

    }
}
