package org.eclipse.e4.babel.core.api;


import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;


public interface IResourceManager {

    KeyTree getKeyTree();

    void addNewKey(final String key);

    void removeKey(final KeyTreeItem keyTreeItem);

    void renameKey(final KeyTreeItem keyTreeItem, final String key);

    CompletableFuture<Void> init(IPropertyResource fileDocument) throws CoreException, IOException;

    SourceEditor getSourceEditor(Locale locale);

    List<SourceEditor> getSourceEditors();

    List<Locale> getLocales();

    BundleGroup getBundleGroup();

    void reloadProperties();

    void save();

    SourceEditor addSourceEditor(IPropertyResource fileDocument, Locale locale);

    IPropertyResource createPropertiesFile(Locale locale) throws CoreException, IOException;

    List<Locale> getSortedLocales();

    String getDisplayName();

    String getResourceLocation();

    boolean containsResource(IPropertyResource resource);

    boolean containsKey(final String key);
}
