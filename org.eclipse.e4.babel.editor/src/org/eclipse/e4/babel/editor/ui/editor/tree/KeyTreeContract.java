package org.eclipse.e4.babel.editor.ui.editor.tree;

import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.ui.editor.BaseView;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;

public interface KeyTreeContract {

    interface View extends BaseView<Presenter> {
        void updateKeyTree(KeyTree keyTree);

        void setTreeViewerContentProvider(KeyTreeContentProvider provider);

        void setTreeViewerLabelProvider(KeyTreeLabelProvider provider);
    }
    
    interface Presenter {

        void addKey(String text);

        boolean isNewKey(String text);

        void init();
     
    }
}
