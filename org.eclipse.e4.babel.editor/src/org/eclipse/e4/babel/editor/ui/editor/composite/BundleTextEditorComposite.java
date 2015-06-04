package org.eclipse.e4.babel.editor.ui.editor.composite;


import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class BundleTextEditorComposite extends Composite {

    private static final int VERTICAL_RULER_WIDTH = 12;

    public BundleTextEditorComposite(final CTabFolder tabFolder) {
        super(tabFolder, SWT.NONE);
        createControl(tabFolder);
    }


    public void createControl(final Composite sashForm) {

        //      Composite parent = new Composite(this, SWT.NONE);
        setLayout(new FillLayout());

        final int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
        final VerticalRuler verticalRuler = new VerticalRuler(VERTICAL_RULER_WIDTH);


        final SourceViewer viewer = new SourceViewer(this, verticalRuler, styles);

    }
}
