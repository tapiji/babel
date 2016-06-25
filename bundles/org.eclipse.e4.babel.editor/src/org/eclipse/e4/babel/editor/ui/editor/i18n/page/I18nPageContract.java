package org.eclipse.e4.babel.editor.ui.editor.i18n.page;


import org.eclipse.e4.babel.editor.ui.BasePresenter;
import org.eclipse.e4.babel.editor.ui.BaseView;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract.View;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryView;


public interface I18nPageContract {

    interface View extends BaseView<Presenter> {

        void onLocaleClick();

        void setNextFocusDown();

        void setNextFocusUp();

        void onFocusChange(I18nPageEntryView bundleEntryComposite);
        
        ResourceBundleEditorContract.View getResourceBundleEditor();

		void selectNextTreeEntry();

		void selectPreviousTreeEntry();
    }

    interface Presenter extends BasePresenter {

    }


}
