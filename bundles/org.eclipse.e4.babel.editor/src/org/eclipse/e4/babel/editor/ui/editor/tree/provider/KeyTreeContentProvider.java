package org.eclipse.e4.babel.editor.ui.editor.tree.provider;


import org.eclipse.e4.babel.editor.model.bundle.listener.IBundleChangeListener;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipselabs.e4.tapiji.logger.Log;



public final class KeyTreeContentProvider implements ITreeContentProvider,IBundleChangeListener {

    private static final String TAG = KeyTreeContentProvider.class.getSimpleName();
    private static Object[] EMPTY_ARRAY = new Object[0];
    private TreeViewer treeViewer;

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.treeViewer = (TreeViewer) viewer;
        if(oldInput != null) {
            ((KeyTree) oldInput).removeChangeListener(this);
        }
        if(newInput != null) {
            ((KeyTree) newInput).addChangeListener(this);
        }
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


    @Override
    public <T> void add(BundleEvent<T> event) {
        treeViewer.refresh(true);
        Log.d(TAG, "add: " + event);
        
    }

    @Override
    public <T> void remove(BundleEvent<T> event) {
        treeViewer.refresh(true);
        Log.d(TAG, "remove: " + event);
    }

    @Override
    public <T> void modify(BundleEvent<T> event) {
        KeyTreeItem treeItem = (KeyTreeItem) event.data();
        Object parentTreeItem = treeItem.getParent();
        treeViewer.refresh(parentTreeItem, true);
        Log.d(TAG, "modify: " + event);
    }

    @Override
    public <T> void select(BundleEvent<T> event) {
        // TODO Auto-generated method stub
        
    }


}
