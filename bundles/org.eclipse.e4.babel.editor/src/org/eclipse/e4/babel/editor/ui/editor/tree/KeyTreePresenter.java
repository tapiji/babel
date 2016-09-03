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

    private KeyTreeLabelProvider keyTreeLabelProvider;
    private KeyTreeContentProvider keyTreeContentProvider;
    private View keyTreeView;
    @Inject
    private IBabelResourceProvider resourceProvider;

    @Override
    public View getKeyTreeView() {
	return keyTreeView;
    }

    private ResourceBundleEditorContract.View resourceBundleEditor;
    private KeyTreeUpdater keyTreeUpdater;

    private KeyTreePresenter(View keyTreeView, ResourceBundleEditorContract.View resourceBundleEditor) {
	this.keyTreeView = keyTreeView;
	this.resourceBundleEditor = resourceBundleEditor;
    }

    @PostConstruct
    public void onCreate() {
	this.keyTreeLabelProvider = new KeyTreeLabelProvider(resourceProvider);
	this.keyTreeContentProvider = new KeyTreeContentProvider();
	this.keyTreeView.setTreeViewerContentProvider(keyTreeContentProvider);
	this.keyTreeView.setTreeViewerLabelProvider(keyTreeLabelProvider);
	this.keyTreeView.updateKeyTree(getKeyTree());
	this.keyTreeUpdater = keyTreeView.getKeyTree().getUpdater();
    }

    @Override
    public void addKey(String key) {
	resourceBundleEditor.getResourceManager().addNewKey(key);
	KeyTreeItem item = (KeyTreeItem) keyTreeView.getStructuredSelectionSelection().getFirstElement();
	if (item != null && item.getId().equals(key)) {
	    resourceBundleEditor.getI18nPage().getPresenter().refreshTextBoxes();
	} else {
	    keyTreeView.setSelectedKeyTreeItem(getKeyTree().getKeyTreeItem(key));
	}
    }

    @Override
    public boolean checkNewKey(String key) {
	boolean keyExist = resourceBundleEditor.getResourceManager().getKeyTree().getBundleGroup().isKey(key);
	if (keyExist || key.length() == 0) {
	    keyTreeView.setButtonAddEnabledState(false);
	} else {
	    keyTreeView.setButtonAddEnabledState(true);
	}
	if (key.length() > 0 && !key.equals(getSelectedKey())) {
	    KeysStartingWithVisitor visitor = new KeysStartingWithVisitor();
	    resourceBundleEditor.getResourceManager().getKeyTree().accept(visitor, key);
	    final KeyTreeItem item = visitor.getKeyTreeItem();
	    if (item != null) {
		keyTreeView.setSyncState(false);
		keyTreeView.setSelectedKeyTreeItem(item);

		if (key.equals(getSelectedKey())) {
		    keyTreeView.getKeyTree().selectKey(getSelectedKey());
		}
	    }
	}
	return keyExist;
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
	return (KeyTreeItem) keyTreeView.getStructuredSelectionSelection().getFirstElement();
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
	KeyTreePresenter presenter = new KeyTreePresenter(keyTreeView, resourceBundleEditor);
	return presenter;
    }

    @Override
    public void init() {
	// TODO Auto-generated method stub

    }
}
