package org.eclipse.e4.babel.editor.ui.editor;

import java.util.Locale;

import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.ui.editor.i18n.page.I18nPageContract;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeView;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

public interface ResourceBundleEditorContract {

    interface View {

	EMenuService getMenuService();

	IBabelResourceProvider getResourceProvider();

	ESelectionService getSelectionService();

	KeyTreeView getKeyTreeView();

	IResourceManager getResourceManager();

	void setActiveTab(Locale locale);

	void addResource(IPropertyResource fileDocument, Locale newLocal);

	void updateDirtyState(boolean dirty);

	boolean getDirtyState();

	I18nPageContract.View getI18nPage();
    }
}
