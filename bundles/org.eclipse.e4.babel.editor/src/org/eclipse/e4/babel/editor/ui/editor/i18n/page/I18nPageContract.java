package org.eclipse.e4.babel.editor.ui.editor.i18n.page;


import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.ui.BasePresenter;
import org.eclipse.e4.babel.editor.ui.BaseView;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract.View;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryView;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.swt.widgets.Composite;


public interface I18nPageContract {

    interface View extends BaseView<Presenter> {

        void onLocaleClick();

        void setNextFocusDown();

        void setNextFocusUp();

        void onFocusChange(I18nPageEntryView bundleEntryComposite);
        
        ResourceBundleEditorContract.View getResourceBundleEditor();

		void selectNextTreeEntry();

		void selectPreviousTreeEntry();

		Composite getI18NPage();

		IResourceManager getResourceManager();

		IBabelResourceProvider getResourceProvider();

		void refreshLayout();
    }

    interface Presenter extends BasePresenter {

    }


}
