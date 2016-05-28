package org.eclipse.e4.babel.editor.ui.editor.tree;


import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeContract.View;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;


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

    public static KeyTreePresenter create(KeyTreeContract.View keyTreeView, KeyTree keyTree) {
        final KeyTreePresenter presenter = new KeyTreePresenter(keyTreeView, new KeyTreeLabelProvider(), new KeyTreeContentProvider(), keyTree);
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


}
