package org.eclipse.e4.babel.editor.ui.editor.i18n.page;

import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntry;

public interface I18nPageContract {

    interface View {
        void onLocaleClick();

        void setNextFocusDown();

        void setNextFocusUp();

        void onFocusChange(I18nPageEntry bundleEntryComposite);
    }
}
