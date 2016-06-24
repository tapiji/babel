package org.eclipse.e4.babel.editor.ui.editor;


import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeView;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;


public interface ResourceBundleEditorContract {

  interface View {

    ECommandService getCommandService();

    EHandlerService getHandlerService();

    EMenuService getMenuService();

    IBabelResourceProvider getResourceProvider();

    ESelectionService getSelectionService();

    KeyTreeView getKeyTreeView();

	IResourceManager getResourceManager();
  }
}
