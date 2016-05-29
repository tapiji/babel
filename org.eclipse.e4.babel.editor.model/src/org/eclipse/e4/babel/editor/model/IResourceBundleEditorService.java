package org.eclipse.e4.babel.editor.model;


import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;


public interface IResourceBundleEditorService {

    void addNewKey(final String key);

    void removeKey(final KeyTreeItem keyTreeItem, final String key);

    void renameKey(final KeyTreeItem keyTreeItem, final String key);

    KeyTree getKeyTree();
}
