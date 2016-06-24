package org.eclipse.e4.babel.core.api;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;

public interface IResourceManager {

	KeyTree getKeyTree();

	void addNewKey(final String key);

	void removeKey(final KeyTreeItem keyTreeItem, final String key);

	void renameKey(final KeyTreeItem keyTreeItem, final String key);

	boolean containsKey(final String key);

	void init(IFile file) throws CoreException;
}
