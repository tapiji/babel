package org.eclipse.e4.babel.editor.ui.editor.i18n.page;

import java.util.List;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract.Presenter;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryView;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public final class I18nPageView extends ScrolledComposite implements I18nPageContract.View {

	private static final String TAG = I18nPageView.class.getSimpleName();

	private I18nPageEntryContract.View activeBundleEntry;
	private Composite i18nEntryComposite;


	private I18nPageContract.Presenter presenter;

	public static I18nPageView create(final Composite sashForm, ResourceBundleEditorContract.View editorView,
			final IBabelResourceProvider resourceProvider, IResourceManager resourceManager) {
		I18nPageView page = new I18nPageView(sashForm, SWT.V_SCROLL | SWT.H_SCROLL);
		I18nPagePresenter presenter = I18nPagePresenter.create(page, resourceManager, editorView, resourceProvider);
		page.setPresenter(presenter);

		return page;
	}

	private I18nPageView(final Composite sashForm,final int style) {
		super(sashForm, style);
		i18nEntryComposite = new Composite(this, SWT.BORDER);
		i18nEntryComposite.setLayout(new GridLayout(1, false));


		createEditingPart();

		this.setContent(i18nEntryComposite);
		this.setMinSize(i18nEntryComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		this.setExpandHorizontal(true);
		this.setExpandVertical(true);
		this.setShowFocusedControl(true);
	}

	private void createEditingPart() {
		Control[] children = i18nEntryComposite.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].dispose();
		}
		presenter.createEditingPages();
	}

	@Override
	public Composite getI18NPage() {
		return i18nEntryComposite;
	}

	@Override
	public void refreshLayout() {
		this.setMinSize(i18nEntryComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		this.layout(true, true);
	}

	@Override
	public void onLocaleClick() {

	}

	@Override
	public void setNextFocusDown() {
		final List<I18nPageEntryContract.View> pageEntries = presenter.getPageEntries();
		if (null != activeBundleEntry) {
			final int index = pageEntries.indexOf(activeBundleEntry);
			if ((index >= 0) && (index != (pageEntries.size() - 1))) {
				setFocusForNextComposite(pageEntries.get(index + 1));
			} else if (index == (pageEntries.size() - 1)) {
				setFocusForNextComposite(pageEntries.get(0));
			}
		}
	}

	@Override
	public void setNextFocusUp() {
		final List<I18nPageEntryContract.View> pageEntries = presenter.getPageEntries();
		if (null != activeBundleEntry) {
			final int index = pageEntries.indexOf(activeBundleEntry);
			if (index > 0) {
				setFocusForNextComposite(pageEntries.get(index - 1));
			} else if (index == 0) {
				setFocusForNextComposite(pageEntries.get(pageEntries.size() - 1));
			}
		}
	}

	private void setFocusForNextComposite(final I18nPageEntryContract.View nextFocusComposite) {
		nextFocusComposite.setFocusTextView();
		setOrigin(getOrigin().x, nextFocusComposite.getCoordinateY());
	}

	@Override
	public void setPresenter(final Presenter presenter) {
		this.presenter = presenter;
		this.presenter.init();
	}

	@Override
	public I18nPageContract.Presenter getPresenter() {
		return presenter;
	}

	@Override
	public void onFocusChange(final I18nPageEntryView bundleEntry) {
		activeBundleEntry = bundleEntry;
	}

	public void refreshPage() {
		presenter.refreshKeyTree();
		createEditingPart();
		i18nEntryComposite.layout(true, true);
		layout(true, true);
	}

	public void dispose() {
		presenter.dispose();
		super.dispose();
	}

}
