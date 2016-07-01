package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;


import java.util.Collection;
import java.util.Locale;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.ui.BasePresenter;
import org.eclipse.e4.babel.editor.ui.BaseView;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPagePresenter.LocalBehaviour;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Image;


public interface I18nPageEntryContract {

    interface View extends BaseView<Presenter> {

        void updateEditorHeight();

        void setFocusTextView();

        int getCoordinateY();

		Presenter getPresenter();

		void updateTextView(IDocument document, boolean enabled);

		void setDuplicateButtonVisibility(boolean visible);

		void setSimilarButtonVisibility(boolean visible);

		String getText();

		void focusTextBox();

		void dispose();

		void addLocalListener(LocalBehaviour localBehaviour);

    }

    interface Presenter extends BasePresenter {

		IResourceManager getResourceManager();

		IBabelResourceProvider getResourceProvider();

		Image loadImage(String imageId);

		Locale getLocale();

		void goToTab();

		void findDuplicates(BundleEntry bundleEntry);

		void findSimilar(BundleEntry bundleEntry);

		void updateDocument(String key);

		boolean isKeyAvailable(String key);

		void updateBundleOnChanges();

		String getActiveKey();

		Collection<BundleEntry> getSimilars();

		Collection<BundleEntry> getDuplicates();

		I18nPageContract.View getI18nPageView();

		boolean isEditorDirty();
    }
}

