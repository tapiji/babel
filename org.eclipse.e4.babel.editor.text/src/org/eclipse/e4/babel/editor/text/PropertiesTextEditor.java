package org.eclipse.e4.babel.editor.text;


import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public class PropertiesTextEditor {

    private static final int VERTICAL_RULER_WIDTH = 12;

    private static final int SOURCE_VIEWER_STYLE = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;

    public PropertiesTextEditor(final Composite parent) {
        final VerticalRuler verticalRuler = new VerticalRuler(VERTICAL_RULER_WIDTH);
        final SourceViewer sourceViewer = new SourceViewer(parent, verticalRuler, SOURCE_VIEWER_STYLE);
    }

}
