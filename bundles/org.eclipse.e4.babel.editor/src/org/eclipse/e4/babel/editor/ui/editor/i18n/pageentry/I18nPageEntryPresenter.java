package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;

import java.util.Locale;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract.View;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.swt.graphics.Image;

public class I18nPageEntryPresenter implements I18nPageEntryContract.Presenter {

	private View view;
	private IResourceManager resourceManager;
	private IBabelResourceProvider resourceProvider;
	private Locale locale;
	private ResourceBundleEditorContract.View editorView;

	private I18nPageEntryPresenter(final View view, final IResourceManager resourceManager,
			final IBabelResourceProvider resourceProvider, Locale locale, ResourceBundleEditorContract.View editorView) {
		this.view = view;
		this.resourceManager = resourceManager;
		this.resourceProvider = resourceProvider;
		this.locale = locale;
		this.editorView = editorView;
	}

	public static I18nPageEntryPresenter create(final I18nPageEntryContract.View pageView,
			final IResourceManager resourceManager, IBabelResourceProvider resourceProvider, Locale locale, ResourceBundleEditorContract.View editorView) {
		I18nPageEntryPresenter presenter = new I18nPageEntryPresenter(pageView, resourceManager, resourceProvider,
				locale,editorView);
		pageView.setPresenter(presenter);
		return presenter;
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
		this.editorView.setActiveTab(locale);
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

}
