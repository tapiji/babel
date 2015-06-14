package org.eclipse.e4.babel.editor.text;


import org.eclipse.e4.babel.editor.text.property.PropertyConfiguration;
import org.eclipse.e4.babel.editor.text.property.PropertyPartitionScanner;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public class PropertiesTextEditor {

    private static final int SOURCE_VIEWER_STYLE = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
    private static final Font FONT = new Font(Display.getCurrent(), "Tahoma", 10, SWT.NORMAL);

    public PropertiesTextEditor(final Composite parent) {
        final LineNumberRulerColumn lineNumberRuleColumn = new LineNumberRulerColumn();
        lineNumberRuleColumn.setFont(FONT);

        final CompositeRuler compositeRuler = new CompositeRuler();
        compositeRuler.addDecorator(1, lineNumberRuleColumn);

        final SourceViewer sourceViewer = new SourceViewer(parent, compositeRuler, SOURCE_VIEWER_STYLE);
        sourceViewer.getTextWidget().setFont(FONT);
        final Document document = new Document();

        sourceViewer.configure(PropertyConfiguration.create());
        final IDocumentPartitioner partitioner = new FastPartitioner(new PropertyPartitionScanner(), PropertyPartitionScanner.PARTITIONS);
        partitioner.connect(document);
        document.setDocumentPartitioner(partitioner);
        sourceViewer.setDocument(document);
    }
}
