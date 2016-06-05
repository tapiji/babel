package org.eclipse.e4.babel.editor.ui.editor.tree;


import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeContract.View;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;


final class KeyTreePresenter implements KeyTreeContract.Presenter {

    private KeyTreeLabelProvider keyTreeLabelProvider;
    private KeyTreeContentProvider keyTreeContentProvider;
    private KeyTree keyTree;
    private View keyTreeView;

    private KeyTreePresenter(View keyTreeView, KeyTreeLabelProvider keyTreeLabelProvider, KeyTreeContentProvider keyTreeContentProvider, KeyTree keyTree) {
        this.keyTreeView = keyTreeView;
        this.keyTreeLabelProvider = keyTreeLabelProvider;
        this.keyTreeContentProvider = keyTreeContentProvider;
        this.keyTree = keyTree;

    }

    public static KeyTreePresenter create(KeyTreeContract.View keyTreeView, KeyTree keyTree, IBabelResourceProvider resourceProvider) {
        final KeyTreePresenter presenter = new KeyTreePresenter(keyTreeView, new KeyTreeLabelProvider(resourceProvider), new KeyTreeContentProvider(), keyTree);
        keyTreeView.setPresenter(presenter);
        return presenter;
    }

    @Override
    public void init() {
        keyTreeView.setTreeViewerContentProvider(keyTreeContentProvider);
        keyTreeView.setTreeViewerLabelProvider(keyTreeLabelProvider);
        keyTreeView.updateKeyTree(keyTree);
    }

    @Override
    public void addKey(String text) {
        // Todo add new key entry
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
        return keyTree;
    }


}
