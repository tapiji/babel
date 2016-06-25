package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;


import java.util.Locale;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.ui.BasePresenter;
import org.eclipse.e4.babel.editor.ui.BaseView;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.swt.graphics.Image;


public interface I18nPageEntryContract {

    interface View extends BaseView<Presenter> {

        void updateEditorHeight();

        void setFocusTextView();

        void addPageListener(I18nPageContract.View pageListener);

        int getCoordinateY();

		void refresh(String key);
    }

    interface Presenter extends BasePresenter {

		IResourceManager getResourceManager();

		IBabelResourceProvider getResourceProvider();

		Image loadImage(String imageId);

		Locale getLocale();

		void goToTab();

    }
}
