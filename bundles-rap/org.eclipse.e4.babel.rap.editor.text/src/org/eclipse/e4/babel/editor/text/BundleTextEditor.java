package org.eclipse.e4.babel.editor.text;

import java.awt.TextArea;

import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

public final class BundleTextEditor extends Composite {
	
	private CTabItem tab;
	private SourceViewer sourceViewer;
	private IPropertyResource sourceViewerDocument;

	public BundleTextEditor(final CTabFolder parent, MDirtyable dirty, IPropertyResource sourceViewerDocument) {
		super(parent, 0);
		 this.sourceViewerDocument = sourceViewerDocument;
		sourceViewer = new SourceViewer(this, 0);
		sourceViewer.setDocument(sourceViewerDocument.getDocument());
		
	
	}

	@Override
	public void dispose() {
		sourceViewerDocument.dispose();
		super.dispose();
	}

	public IPropertyResource getSourceViewerDocument() {
		 return sourceViewerDocument;
	}

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
