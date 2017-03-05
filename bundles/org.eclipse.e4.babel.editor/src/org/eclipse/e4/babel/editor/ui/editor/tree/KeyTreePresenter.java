package org.eclipse.e4.babel.editor.ui.editor.tree;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.model.tree.visitor.KeysStartingWithVisitor;
import org.eclipse.e4.babel.editor.model.updater.FlatKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.GroupedKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;
import org.eclipse.e4.babel.editor.ui.editor.ResourceBundleEditorContract;
import org.eclipse.e4.babel.editor.ui.editor.tree.KeyTreeContract.View;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeContentProvider;
import org.eclipse.e4.babel.editor.ui.editor.tree.provider.KeyTreeLabelProvider;
import org.eclipse.e4.babel.resource.IBabelResourceProvider;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

@Creatable
final class KeyTreePresenter implements KeyTreeContract.Presenter {

    private KeyTreeLabelProvider treeLabelProvider;
    private KeyTreeContentProvider treeContentProvider;
    private ResourceBundleEditorContract.View resourceBundleEditor;
    private View view;

    @Inject
    private IBabelResourceProvider resourceProvider;

    private KeyTreePresenter(View keyTreeView, ResourceBundleEditorContract.View resourceBundleEditor) {
	this.view = keyTreeView;
	this.resourceBundleEditor = resourceBundleEditor;
    }

    @PostConstruct
    public void onCreate() {

    }

    @Override
    public void addKey(String key) {
	resourceBundleEditor.getResourceManager().addNewKey(key);
	KeyTreeItem item = (KeyTreeItem) view.getStructuredSelectionSelection().getFirstElement();
	if (item != null && item.getId().equals(key)) {
	    resourceBundleEditor.getI18nPage().getPresenter().refreshTextBoxes();
	} else {
	    view.setSelectedKeyTreeItem(getKeyTree().getKeyTreeItem(key));
	}
    }

    @Override
    public View getKeyTreeView() {
	return view;
    }

    @Override
    public boolean checkNewKey(String key) {
	boolean keyExist = resourceBundleEditor.getResourceManager().getKeyTree().getBundleGroup().isKey(key);
	if (keyExist || key.length() == 0) {
	    view.setButtonAddEnabledState(false);
	} else {
	    view.setButtonAddEnabledState(true);
	}
	if (key.length() > 0 && !key.equals(getSelectedKey())) {
	    KeysStartingWithVisitor visitor = new KeysStartingWithVisitor();
	    resourceBundleEditor.getResourceManager().getKeyTree().accept(visitor, key);
	    final KeyTreeItem item = visitor.getKeyTreeItem();
	    if (item != null) {
		view.setSyncState(false);
		view.setSelectedKeyTreeItem(item);

		if (key.equals(getSelectedKey())) {
		    view.getKeyTree().selectKey(getSelectedKey());
		}
	    }
	}
	return keyExist;
    }

    @Override
    public void createTreeView() {
	this.treeLabelProvider = new KeyTreeLabelProvider(resourceProvider);
	this.treeContentProvider = new KeyTreeContentProvider();
	this.view.setTreeViewerContentProvider(treeContentProvider);
	this.view.setTreeViewerLabelProvider(treeLabelProvider);
	this.view.updateKeyTree(getKeyTree());
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
    public String getSelectedKey() {
	String key = null;
	KeyTreeItem item = getSelection();
	if (item != null) {
	    key = item.getId();
	}
	return key;
    }

    @Override
    public KeyTreeItem getSelection() {
	return (KeyTreeItem) view.getStructuredSelectionSelection().getFirstElement();
    }

    @Override
    public KeyTree getKeyTree() {
	return resourceBundleEditor.getResourceManager().getKeyTree();
    }

    @Override
    public void dispose() {
	treeContentProvider.dispose();
	treeLabelProvider.dispose();
    }

    @Override
    public void changeToHierarchicalTree() {
	getKeyTree().setUpdater(new GroupedKeyTreeUpdater(PropertyPreferences.getInstance().getKeyGroupSeparator()));
    }

    @Override
    public void changeToFlatTree() {
	getKeyTree().setUpdater(new FlatKeyTreeUpdater());
    }

    @Override
    public KeyTreeUpdater getKeyTreeUpdater() {
	return view.getKeyTree().getKeyTreeUpdater();
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
	KeyTreePresenter presenter = new KeyTreePresenter(keyTreeView, resourceBundleEditor);
	return presenter;
    }

}
