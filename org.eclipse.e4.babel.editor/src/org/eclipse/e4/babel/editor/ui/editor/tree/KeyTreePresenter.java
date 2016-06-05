package org.eclipse.e4.babel.editor.ui.editor.tree;


import org.eclipse.e4.babel.editor.model.IResourceBundleEditorService;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeContract.View;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;


final class KeyTreePresenter implements KeyTreeContract.Presenter {

    private KeyTreeLabelProvider keyTreeLabelProvider;
    private KeyTreeContentProvider keyTreeContentProvider;
    private IResourceBundleEditorService editorService;
    private View keyTreeView;

    private KeyTreePresenter(View keyTreeView, KeyTreeLabelProvider keyTreeLabelProvider, KeyTreeContentProvider keyTreeContentProvider, IResourceBundleEditorService editorService) {
        this.keyTreeView = keyTreeView;
        this.keyTreeLabelProvider = keyTreeLabelProvider;
        this.keyTreeContentProvider = keyTreeContentProvider;
        this.editorService = editorService;

    }

    public static KeyTreePresenter create(KeyTreeContract.View keyTreeView, ResourceBundleEditorContract.View resourceBundleEditor) {
        final KeyTreePresenter presenter = new KeyTreePresenter(keyTreeView, new KeyTreeLabelProvider(resourceBundleEditor.getResourceProvider()), new KeyTreeContentProvider(), resourceBundleEditor.getEditorService());
        keyTreeView.setPresenter(presenter);
        return presenter;
    }

    @Override
    public void init() {
        keyTreeView.setTreeViewerContentProvider(keyTreeContentProvider);
        keyTreeView.setTreeViewerLabelProvider(keyTreeLabelProvider);
        keyTreeView.updateKeyTree(getKeyTree());
    }
    
    @Override
    public void addKey(String key) {
        editorService.addNewKey(key);
        keyTreeView.setSelectedKeyTreeItem(getKeyTree().getKeyTreeItem(key));
    }

    @Override
    public boolean isNewKey(String text) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String getSelectedKey(final KeyTreeItem item) {
        String key = null;
        if (item != null) {
            key = item.getId();
        }
        return key;
    }

    @Override
    public String getSelectedKeyFromSelection(IStructuredSelection selection) {
        return getSelectedKey(getSelection(selection));
    }


    @Override
    public KeyTreeItem getSelection(IStructuredSelection selection) {
        return (KeyTreeItem) selection.getFirstElement();
    }
    
    @Override
    public KeyTree getKeyTree() {
        return editorService.getKeyTree();
    }


}
