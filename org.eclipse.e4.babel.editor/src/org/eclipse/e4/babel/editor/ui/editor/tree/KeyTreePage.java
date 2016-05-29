package org.eclipse.e4.babel.editor.ui.editor.tree;


import org.eclipse.e4.babel.editor.model.IResourceBundleEditorService;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.custom.SashForm;


public class KeyTreePage {

    private KeyTreeContract.View view;
    private KeyTreeContract.Presenter presenter;

    private KeyTreePage(SashForm sashForm, EMenuService menuService, IBabelResourceProvider resourceProvider, ESelectionService selectionService, IResourceBundleEditorService editor) {        
        view = KeyTreeView.create(sashForm, menuService, resourceProvider, editor.getKeyTree(),selectionService);
    }

    public KeyTreeContract.View getView() {
        return view;
    }

    public KeyTreeContract.Presenter getPresenter() {
        return presenter;
    }

    public static KeyTreePage create(SashForm sashForm, EMenuService menuService, IBabelResourceProvider resourceProvider, ESelectionService selectionService, IResourceBundleEditorService editor) {
        return new KeyTreePage(sashForm, menuService, resourceProvider,selectionService, editor);
    }
}
