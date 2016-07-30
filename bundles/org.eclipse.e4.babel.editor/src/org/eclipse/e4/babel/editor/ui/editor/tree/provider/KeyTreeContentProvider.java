package org.eclipse.e4.babel.editor.ui.editor.tree.provider;

import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.model.bundle.listener.IBundleChangeListener;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.logger.Log;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

public final class KeyTreeContentProvider implements ITreeContentProvider, IBundleChangeListener {

    private static final String TAG = KeyTreeContentProvider.class.getSimpleName();
    private static Object[] EMPTY_ARRAY = new Object[0];
    private TreeViewer treeViewer;

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	this.treeViewer = (TreeViewer) viewer;
	if (oldInput != null) {
	    ((KeyTree) oldInput).removeChangeListener(this);
	}
	if (newInput != null) {
	    ((KeyTree) newInput).addChangeListener(this);
	}
    }

    @Override
    public Object[] getElements(final Object inputElement) {
	return getChildren(inputElement);
    }

    @Override
    public Object[] getChildren(final Object parentElement) {
	if (parentElement instanceof KeyTree) {
	    return ((KeyTree) parentElement).getRootKeyItems().toArray();
	} else if (parentElement instanceof KeyTreeItem) {
	    return ((KeyTreeItem) parentElement).getChildren().toArray();
	}
	return EMPTY_ARRAY;
    }

    @Override
    public Object getParent(final Object element) {
	if (element instanceof KeyTreeItem) {
	    return ((KeyTreeItem) element).getParent();
	}
	return null;
    }

    @Override
    public boolean hasChildren(final Object element) {
	return getChildren(element).length > 0;
    }

    @Override
    public <T> void add(final BundleEvent<T> event) {
	treeViewer.refresh(true);
	Log.d(TAG, "add: " + event);
    }

    @Override
    public <T> void remove(final BundleEvent<T> event) {
	treeViewer.refresh(true);
	Log.d(TAG, "remove: " + event);
    }

    @Override
    public <T> void modify(final BundleEvent<T> event) {
	KeyTreeItem treeItem = (KeyTreeItem) event.data();
	treeViewer.refresh(treeItem.getParent(), true);
	Log.d(TAG, "modify: " + event);
    }

    @Override
    public <T> void select(final BundleEvent<T> event) {
	KeyTreeItem treeItem = (KeyTreeItem) event.data();
	if (treeItem != null) {
	    KeyTreeItem currentSelection = getTreeSelection();
	    if ((currentSelection == null) || (!treeItem.getId().endsWith(currentSelection.getId()))) {
		StructuredSelection selection = new StructuredSelection(treeItem);
		treeViewer.setSelection(selection);
	    }
	}
    }

    private KeyTreeItem getTreeSelection() {
	IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
	return ((KeyTreeItem) selection.getFirstElement());
    }
}
