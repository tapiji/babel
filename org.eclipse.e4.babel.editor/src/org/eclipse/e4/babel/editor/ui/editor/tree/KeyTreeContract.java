package org.eclipse.e4.babel.editor.ui.editor.tree;


import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.ui.BasePresenter;
import org.eclipse.e4.babel.editor.ui.BaseView;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;


public interface KeyTreeContract {

    interface View extends BaseView<Presenter> {

        void updateKeyTree(KeyTree keyTree);

        void setTreeViewerContentProvider(KeyTreeContentProvider provider);

        void setTreeViewerLabelProvider(KeyTreeLabelProvider provider);

        void setSelectedKeyTreeItem(KeyTreeItem item);
        
        
    }

    interface Presenter extends BasePresenter {

        void addKey(String text);

        boolean isNewKey(String text);

        String getSelectedKey(KeyTreeItem item);

        KeyTreeItem getSelection(IStructuredSelection selection);

        String getSelectedKeyFromSelection(IStructuredSelection selection);

        KeyTree getKeyTree();

        void changeToHierarchicalTree();

        void changeToFlatTree();
        
    }
}

