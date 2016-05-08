package org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry;


public class I18nPageEntryPresenter implements I18nPageEntryContract.Presenter {

    private I18nPageEntryPresenter() {

    }


    public static I18nPageEntryPresenter create() {
        return new I18nPageEntryPresenter();
    }
}
