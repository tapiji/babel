package org.eclipse.e4.babel.editor.model;

import org.eclipse.e4.babel.editor.model.tree.KeyTree;

public interface IResourceBundleEditorService {

    void addNewKey(final String key);
    
    void removeKey(final String key);

    void renameKey(final String key);

    KeyTree getKeyTree();
}
