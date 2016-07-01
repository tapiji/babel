package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.checks.proximity.LevenshteinDistanceAnalyzer;
import org.eclipse.e4.babel.editor.model.checks.proximity.ProximityAnalyzer;
import org.eclipse.e4.babel.editor.model.checks.proximity.WordCountAnalyzer;
import org.eclipse.e4.babel.editor.model.checks.visitor.DuplicateValuesVisitor;
import org.eclipse.e4.babel.editor.model.checks.visitor.SimilarValuesVisitor;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract.View;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Image;

public final class I18nPageEntryPresenter implements I18nPageEntryContract.Presenter {

    private View view;
    private IResourceManager resourceManager;
    private IBabelResourceProvider resourceProvider;
    private Locale locale;
    private I18nPageContract.View i18nPageView;
    private DuplicateValuesVisitor duplVisitor;
    private SimilarValuesVisitor similarVisitor;
    private String activeKey;
    private ResourceBundleEditorContract.View resourceBundleEditor;

    private I18nPageEntryPresenter(final View view, Locale locale, I18nPageContract.View i18nPageView) {
	this.view = view;
	this.resourceManager = i18nPageView.getPresenter().getResourceManager();
	this.resourceProvider = i18nPageView.getPresenter().getResourceProvider();
	this.locale = locale;
	this.i18nPageView = i18nPageView;
	this.resourceBundleEditor = i18nPageView.getPresenter().getResourceBundleEditor();
    }

    public static I18nPageEntryPresenter create(final I18nPageEntryContract.View pageView, Locale locale, I18nPageContract.View i18nPageView) {
	I18nPageEntryPresenter presenter = new I18nPageEntryPresenter(pageView, locale, i18nPageView);
	pageView.setPresenter(presenter);
	return presenter;
    }

    @Override
    public I18nPageContract.View getI18nPageView() {
	return i18nPageView;
    }

    @Override
    public IBabelResourceProvider getResourceProvider() {
	return this.resourceProvider;
    }

    @Override
    public IResourceManager getResourceManager() {
	return resourceManager;
    }

    @Override
    public Image loadImage(final String imageId) {
	return resourceProvider.loadImage(imageId);
    }

    @Override
    public void goToTab() {
	this.resourceBundleEditor.setActiveTab(locale);
    }

    @Override
    public Locale getLocale() {
	return this.locale;
    }

    @Override
    public void init() {
	// TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
	// TODO Auto-generated method stub

    }

    @Override
    public void updateBundleOnChanges() {
	if (activeKey != null) {
	    final BundleGroup bundleGroup = resourceManager.getBundleGroup();
	    final BundleEntry entry = bundleGroup.getBundleEntry(locale, activeKey);
	    final String text = view.getText();
	    if (entry == null || !text.equals(entry.getValue())) {
		bundleGroup.addBundleEntry(locale, BundleEntry.create(activeKey, text));
		resourceBundleEditor.updateDirtyState(true);
	    }
	}
    }

    @Override
    public String getActiveKey() {
	return this.activeKey;
    }

    @Override
    public void findDuplicates(BundleEntry bundleEntry) {
	BundleGroup bundleGroup = resourceManager.getBundleGroup();
	if (duplVisitor == null) {
	    duplVisitor = new DuplicateValuesVisitor();
	}
	duplVisitor.clear();
	bundleGroup.getBundle(locale).accept(duplVisitor, bundleEntry);
	view.setDuplicateButtonVisibility(duplVisitor.getDuplicates().size() > 0);
    }

    @Override
    public void findSimilar(BundleEntry bundleEntry) {
	ProximityAnalyzer analyzer;
	if (PropertyPreferences.getInstance().isReportSimilarValuesLevensthein()) {
	    analyzer = LevenshteinDistanceAnalyzer.getInstance();
	} else {
	    analyzer = WordCountAnalyzer.getInstance();
	}
	BundleGroup bundleGroup = resourceManager.getBundleGroup();
	if (similarVisitor == null) {
	    similarVisitor = new SimilarValuesVisitor();
	}
	similarVisitor.setProximityAnalyzer(analyzer);
	similarVisitor.clear();
	bundleGroup.getBundle(locale).accept(similarVisitor, bundleEntry);
	if (duplVisitor != null) {
	    similarVisitor.getSimilars().removeAll(duplVisitor.getDuplicates());
	}
	view.setSimilarButtonVisibility(similarVisitor.getSimilars().size() > 0);
    }

    @Override
    public boolean isKeyAvailable(final String key) {
	return key != null && getResourceManager().getBundleGroup().isKey(key);
    }

    @Override
    public void updateDocument(final String key) {
	this.activeKey = key;
	final IDocument document = new Document();
	final BundleGroup bundleGroup = getResourceManager().getBundleGroup();
	if (isKeyAvailable(key)) {
	    BundleEntry bundleEntry = bundleGroup.getBundleEntry(locale, key);
	    if (bundleEntry == null) {
		document.set("");
	    } else {
		document.set(bundleEntry.getValue());
	    }
	    if (PropertyPreferences.getInstance().isReportDuplicateValues()) {
		findDuplicates(bundleEntry);
	    } else {
		duplVisitor = null;
	    }
	    if (PropertyPreferences.getInstance().isReportSimilarValues()) {
		findSimilar(bundleEntry);
	    } else {
		similarVisitor = null;
	    }
	    view.updateTextView(document, true);
	} else {
	    document.set("");
	    view.updateTextView(document, false);
	}
    }

    @Override
    public Collection<BundleEntry> getSimilars() {
	return Collections.emptyList();//similarVisitor.getSimilars();
    }

    @Override
    public Collection<BundleEntry> getDuplicates() {
	return Collections.emptyList();//duplVisitor.getDuplicates();
    }

    @Override
    public boolean isEditorDirty() {
	return resourceBundleEditor.getDirtyState();
    }
}
