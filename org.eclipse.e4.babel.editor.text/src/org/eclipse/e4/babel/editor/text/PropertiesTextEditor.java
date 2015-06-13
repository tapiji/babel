package org.eclipse.e4.babel.editor.text;


import javax.annotation.PostConstruct;
import org.eclipse.e4.babel.editor.text.prop.PropertyConfiguration;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipselabs.e4.tapiji.logger.Log;


public class PropertiesTextEditor {

    private static final int VERTICAL_RULER_WIDTH = 50;

    private static final int SOURCE_VIEWER_STYLE = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;


    public PropertiesTextEditor() {
        // final VerticalRuler verticalRuler = new VerticalRuler(VERTICAL_RULER_WIDTH);

    }


    @PostConstruct
    public void createControl(final Composite parent) {

        Log.d("asd", "ssdsdsndsadsaidasidasudahuidasidsdausdiasdiasdh");

        LineNumberRulerColumn lineNumberRuleColumn = new LineNumberRulerColumn();
        //Color color = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
        //lineNumberRuleColumn.setBackground(color);
        CompositeRuler compositeRuler = new CompositeRuler();
        compositeRuler.addDecorator(1, lineNumberRuleColumn);


        IVerticalRuler verticalRuler = compositeRuler;

        // CompositeRuler ruler = new CompositeRuler();
        //LineNumberRulerColumn lnrc = new LineNumberRulerColumn();
        //  ruler.addDecorator(0, lnrc);
        final SourceViewer sourceViewer = new SourceViewer(parent, verticalRuler, SOURCE_VIEWER_STYLE);
        sourceViewer.setDocument(new Document());
        sourceViewer.configure(PropertyConfiguration.create());
    }
}
