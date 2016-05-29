package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;


import org.eclipse.e4.babel.editor.ui.BasePresenter;
import org.eclipse.e4.babel.editor.ui.BaseView;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;


public interface I18nPageEntryContract {

    interface View extends BaseView<Presenter> {

        void updateEditorHeight();

        void setFocusTextView();

        void addPageListener(I18nPageContract.View pageListener);

        int getCoordinateY();
    }

    interface Presenter extends BasePresenter {

    }
}
