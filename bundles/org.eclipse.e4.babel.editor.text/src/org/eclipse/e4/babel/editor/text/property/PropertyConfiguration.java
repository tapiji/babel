package org.eclipse.e4.babel.editor.text.property;


import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;


public final class PropertyConfiguration extends SourceViewerConfiguration {

    private PropertyConfiguration() {
        super();
    }

    @Override
    public String[] getConfiguredContentTypes(final ISourceViewer sourceViewer) {
        return PropertyPartitionScanner.PARTITIONS;
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer) {
        final PresentationReconciler reconciler = new PresentationReconciler();

        DefaultDamagerRepairer defaultDamagerRepairer = new DefaultDamagerRepairer(PropertyDefaultScanner.getInstance());
        reconciler.setDamager(defaultDamagerRepairer, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(defaultDamagerRepairer, IDocument.DEFAULT_CONTENT_TYPE);

        defaultDamagerRepairer = new DefaultDamagerRepairer(PropertyValueScanner.getInstance());
        reconciler.setDamager(defaultDamagerRepairer, PropertyPartitionScanner.PROPERTY_VALUE);
        reconciler.setRepairer(defaultDamagerRepairer, PropertyPartitionScanner.PROPERTY_VALUE);

        defaultDamagerRepairer = new DefaultDamagerRepairer(PropertyCommentScanner.getInstance());
        reconciler.setDamager(defaultDamagerRepairer, PropertyPartitionScanner.PROPERTY_COMMENT);
        reconciler.setRepairer(defaultDamagerRepairer, PropertyPartitionScanner.PROPERTY_COMMENT);

        return reconciler;
    }

    public static PropertyConfiguration create() {
        return new PropertyConfiguration();
    }
}
