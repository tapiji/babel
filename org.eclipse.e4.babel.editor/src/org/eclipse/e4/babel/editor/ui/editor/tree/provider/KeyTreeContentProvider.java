package org.eclipse.e4.babel.editor.ui.editor.tree.provider;


import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;


public final class KeyTreeContentProvider implements ITreeContentProvider {

    private static Object[] EMPTY_ARRAY = new Object[0];
    private TreeViewer treeViewer;

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.treeViewer = (TreeViewer) viewer;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if(parentElement instanceof KeyTree) {
            return ((KeyTree) parentElement).getRootKeyItems().toArray(); 
        } else if (parentElement instanceof KeyTreeItem) {
            return ((KeyTreeItem) parentElement).getChildren().toArray(); 
        }
        return EMPTY_ARRAY;
    }

    @Override
    public Object getParent(Object element) {
        if(element instanceof KeyTreeItem) {
            return ((KeyTreeItem) element).getParent();
        }
        return null;
    }


    
    @Override
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }


}
