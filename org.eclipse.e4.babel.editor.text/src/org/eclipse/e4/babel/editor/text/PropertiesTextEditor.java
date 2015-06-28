package org.eclipse.e4.babel.editor.text;


import org.eclipse.core.resources.IFile;
import org.eclipse.e4.babel.editor.text.document.SourceViewerDocument;
import org.eclipse.e4.babel.editor.text.property.PropertyConfiguration;
import org.eclipse.e4.babel.editor.text.property.PropertyPartitionScanner;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public final class PropertiesTextEditor {

    private static final int SOURCE_VIEWER_STYLE = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
    private static final Font FONT = new Font(Display.getCurrent(), "Tahoma", 10, SWT.NORMAL);
    private SourceViewer sourceViewer;
    private SourceViewerDocument sourceViewerDocument;

    public PropertiesTextEditor(final Composite parent, final IFile file) {
        final LineNumberRulerColumn lineNumberRuleColumn = new LineNumberRulerColumn();
        lineNumberRuleColumn.setFont(FONT);

        final CompositeRuler compositeRuler = new CompositeRuler();
        compositeRuler.addDecorator(1, lineNumberRuleColumn);

        sourceViewer = new SourceViewer(parent, compositeRuler, SOURCE_VIEWER_STYLE);
        sourceViewer.getTextWidget().setFont(FONT);
        sourceViewer.configure(PropertyConfiguration.create());

        sourceViewerDocument = SourceViewerDocument.create(file);
        final IDocument document = sourceViewerDocument.getDocument();

        final IDocumentPartitioner partitioner = new FastPartitioner(new PropertyPartitionScanner(), PropertyPartitionScanner.PARTITIONS);
        partitioner.connect(document);
        document.setDocumentPartitioner(partitioner);
        sourceViewer.setDocument(document);
    }

    @NonNull
    public SourceViewerDocument getSourceViewerDocument() {
        return sourceViewerDocument;
    }

    @NonNull
    public SourceViewer getSourceViewer() {
        return sourceViewer;
    }
}
