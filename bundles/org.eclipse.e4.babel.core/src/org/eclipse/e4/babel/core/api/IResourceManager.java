package org.eclipse.e4.babel.core.api;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;

public interface IResourceManager {

	KeyTree getKeyTree();

	void addNewKey(final String key);

	void removeKey(final KeyTreeItem keyTreeItem, final String key);

	void renameKey(final KeyTreeItem keyTreeItem, final String key);

	boolean containsKey(final String key);

	void init(IFile file) throws CoreException;

	SourceEditor getSourceEditor(Locale locale);

	List<SourceEditor> getSourceEditors();

	List<Locale> getLocales();

	BundleGroup getBundleGroup();

	void reloadProperties();

	void save();

	SourceEditor addSourceEditor(IFile resource, Locale locale);

	IFile createPropertiesFile(Locale locale) throws CoreException, IOException;
}
