package org.eclipse.e4.babel.editor.text;


import org.eclipse.e4.babel.editor.text.document.IFileDocument;
import org.eclipse.e4.babel.editor.text.property.PropertyConfiguration;
import org.eclipse.e4.babel.editor.text.property.PropertyPartitionScanner;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public final class BundleTextEditor extends Composite {

    private static final int SOURCE_VIEWER_STYLE = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
    private static final Font FONT = new Font(Display.getCurrent(), "Tahoma", 10, SWT.NORMAL);
    private SourceViewer sourceViewer;
    private IFileDocument sourceViewerDocument;
    private CTabItem tab;

    public BundleTextEditor(final CTabFolder parent, MDirtyable dirty, IFileDocument sourceViewerDocument) {
        super(parent, SWT.NONE);
        setLayout(new FillLayout());
        this.sourceViewerDocument = sourceViewerDocument;
        final LineNumberRulerColumn lineNumberRuleColumn = new LineNumberRulerColumn();
        lineNumberRuleColumn.setFont(FONT);

        final CompositeRuler compositeRuler = new CompositeRuler();
        compositeRuler.addDecorator(1, lineNumberRuleColumn);
        compositeRuler.addDecorator(2, new AnnotationRulerColumn(20));

        sourceViewer = new SourceViewer(this, compositeRuler, SOURCE_VIEWER_STYLE);
        sourceViewer.getTextWidget().setFont(FONT);
        sourceViewer.configure(PropertyConfiguration.create());

        final IDocument document = sourceViewerDocument.getDocument();

        final IDocumentPartitioner partitioner = new FastPartitioner(new PropertyPartitionScanner(), PropertyPartitionScanner.PARTITIONS);
        partitioner.connect(document);
        document.setDocumentPartitioner(partitioner);
        sourceViewer.setDocument(document);

        sourceViewer.addTextListener(new ITextListener() {

            @Override
            public void textChanged(TextEvent event) {
                if (!dirty.isDirty()) {
                    dirty.setDirty(true);
                }
            }
        });

    }

    @NonNull
    public IFileDocument getSourceViewerDocument() {
        return sourceViewerDocument;
    }

    @NonNull
    public SourceViewer getSourceViewer() {
        return sourceViewer;
    }

    public void setTab(CTabItem createTab) {
        this.tab = createTab;
    }


    public CTabItem getTab() {
        return this.tab;
    }

}
