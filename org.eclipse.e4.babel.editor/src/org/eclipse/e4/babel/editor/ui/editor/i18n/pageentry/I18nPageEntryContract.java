package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;


import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;


public interface I18nPageEntryContract {

    interface View {

        void updateEditorHeight();

        void setFocusTextView();

        void addPageListener(I18nPageContract.View pageListener);

        int getCoordinateY();
    }

    interface Presenter {

    }
}
