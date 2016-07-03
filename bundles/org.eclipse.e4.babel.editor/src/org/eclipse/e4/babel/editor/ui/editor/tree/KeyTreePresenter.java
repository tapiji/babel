package org.eclipse.e4.babel.editor.ui.editor.tree;

import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.model.updater.FlatKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.GroupedKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeContract.View;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

final class KeyTreePresenter implements KeyTreeContract.Presenter {

    private KeyTreeLabelProvider keyTreeLabelProvider;
    private KeyTreeContentProvider keyTreeContentProvider;
    private View keyTreeView;
    private ResourceBundleEditorContract.View resourceBundleEditor;
    private KeyTreeUpdater keyTreeUpdater;

    private KeyTreePresenter(View keyTreeView, KeyTreeLabelProvider keyTreeLabelProvider, KeyTreeContentProvider keyTreeContentProvider,
	    ResourceBundleEditorContract.View resourceBundleEditor) {
	this.keyTreeView = keyTreeView;
	this.keyTreeLabelProvider = keyTreeLabelProvider;
	this.keyTreeContentProvider = keyTreeContentProvider;
	this.resourceBundleEditor = resourceBundleEditor;
    }

    @Override
    public void init() {
	keyTreeView.createView();
	keyTreeView.setTreeViewerContentProvider(keyTreeContentProvider);
	keyTreeView.setTreeViewerLabelProvider(keyTreeLabelProvider);
	keyTreeView.updateKeyTree(getKeyTree());
	keyTreeUpdater = keyTreeView.getKeyTree().getUpdater();
    }

    @Override
    public void addKey(String key) {
	resourceBundleEditor.getResourceManager().addNewKey(key);
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
	return resourceBundleEditor.getResourceManager().getKeyTree();
    }

    @Override
    public void dispose() {
	keyTreeContentProvider.dispose();
	keyTreeLabelProvider.dispose();
    }

    @Override
    public void changeToHierarchicalTree() {
	keyTreeUpdater = new GroupedKeyTreeUpdater(PropertyPreferences.getInstance().getKeyGroupSeparator());
	getKeyTree().setUpdater(keyTreeUpdater);
    }

    @Override
    public void changeToFlatTree() {
	keyTreeUpdater = new FlatKeyTreeUpdater();
	getKeyTree().setUpdater(keyTreeUpdater);
    }

    @Override
    public KeyTreeUpdater getKeyTreeUpdater() {
        return keyTreeUpdater;
    }

    @Override
    public ViewerFilter getTreeFilter() {
	return new ViewerFilter() {
	    @Override
	    public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof KeyTreeItem) {
		    KeyTreeItem item = (KeyTreeItem) element;
		    return item.isVisible();
		}
		return true;
	    }
	};
    }

    public static KeyTreePresenter create(KeyTreeContract.View keyTreeView, ResourceBundleEditorContract.View resourceBundleEditor) {
	KeyTreePresenter presenter = new KeyTreePresenter(keyTreeView, new KeyTreeLabelProvider(resourceBundleEditor.getResourceProvider()), new KeyTreeContentProvider(),
		resourceBundleEditor);
	return presenter;
    }
}
