package org.eclipse.e4.babel.editor.ui.editor.i18n.page;


import java.util.List;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.ui.BasePresenter;
import org.eclipse.e4.babel.editor.ui.BaseView;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract.View;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract.Presenter;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPagePresenter.LocalBehaviour;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryContract;
import org.eclipse.e4.babel.editor.ui.editor.i18n.pageentry.I18nPageEntryView;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.swt.widgets.Composite;


public interface I18nPageContract {

    interface View extends BaseView<Presenter> {

        void onLocaleClick();

        void setNextFocusDown();

        void setNextFocusUp();

        void onFocusChange(I18nPageEntryView bundleEntryComposite);

		Composite getI18NPage();

		void refreshLayout();

		Presenter getPresenter();

		void createEditingPart();
    }

    interface Presenter extends BasePresenter {

		void createEditingPages();

		IResourceManager getResourceManager();

		LocalBehaviour getLocalBehaviour();

		List<I18nPageEntryContract.View> getPageEntries();

		void redrawEditorSize();

		I18nPageEntryContract.View getActiveEntry();

		void selectPreviousTreeEntry();

		void selectNextTreeEntry();

		ResourceBundleEditorContract.View getResourceBundleEditor();

		IBabelResourceProvider getResourceProvider();

		void refreshKeyTree();

		void refreshEditorOnChanges();

		void refreshTextBoxes();

    }


}
