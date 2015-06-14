package org.eclipse.e4.babel.editor.text.property;


import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;


public final class PropertyConfiguration extends SourceViewerConfiguration {

    private PropertyConfiguration() {

    }

    @Override
    public String[] getConfiguredContentTypes(final ISourceViewer sourceViewer) {
        return PropertyPartitionScanner.PARTITIONS;
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer) {
        final PresentationReconciler reconciler = new PresentationReconciler();

        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(PropertyDefaultScanner.getInstance());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        dr = new DefaultDamagerRepairer(PropertyValueScanner.getInstance());
        reconciler.setDamager(dr, PropertyPartitionScanner.PROPERTY_VALUE);
        reconciler.setRepairer(dr, PropertyPartitionScanner.PROPERTY_VALUE);

        dr = new DefaultDamagerRepairer(PropertyCommentScanner.getInstance());
        reconciler.setDamager(dr, PropertyPartitionScanner.PROPERTY_COMMENT);
        reconciler.setRepairer(dr, PropertyPartitionScanner.PROPERTY_COMMENT);

        return reconciler;
    }

    public static PropertyConfiguration create() {
        return new PropertyConfiguration();
    }
}
