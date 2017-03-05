/*
 * Copyright (C) 2003-2014 Pascal Essiembre
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.e4.babel.core.internal.resource;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.api.IResourceFactory;
import org.eclipse.e4.babel.core.api.IResourceManager;
import org.eclipse.e4.babel.core.internal.generator.PropertiesGenerator;
import org.eclipse.e4.babel.core.internal.parser.PropertiesParser;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.core.utils.UIUtils;
import org.eclipse.e4.babel.editor.model.bundle.Bundle;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleChangeAdapter;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.model.tree.KeyTree;
import org.eclipse.e4.babel.editor.model.tree.KeyTreeItem;
import org.eclipse.e4.babel.editor.model.updater.FlatKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.GroupedKeyTreeUpdater;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;
import org.eclipse.e4.babel.logger.Log;


/**
 * Mediator holding instances of commonly used items, dealing with
 * important interactions within themselves.
 *
 * @author Pascal Essiembre
 * @author Alexander Bieber
 * @author Christian Behon
 */

public final class ResourceManager implements IResourceManager {

    private static final String TAG = ResourceManager.class.getSimpleName();

    private IResourceFactory resourcesFactory;
    private BundleGroup bundleGroup;
    private KeyTree keyTree;

    private final Map<Locale, SourceEditor> sourceEditors = new HashMap<>();
    private final List<Locale> locales = new ArrayList<>();

    private ExecutorService executorService;

    public ResourceManager() {
        super();
        executorService = Executors.newFixedThreadPool(1);
    }

    /**
     * Constructor.
     *
     * @param site eclipse editor site
     * @param file file used to create manager
     * @return
     * @throws CoreException problem creating resource manager
     * @throws IOException
     */
    @Override
    public CompletableFuture<Void> init(final IPropertyResource fileDocument) throws CoreException, IOException {

        return CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();

            try {
                resourcesFactory = ResourceFactory.createFactory(fileDocument);
                bundleGroup = BundleGroup.create();
                resourcesFactory.getSourceEditors().forEach(editor -> {
                    final Locale locale = editor.getLocale();
                    sourceEditors.put(locale, editor);
                    locales.add(locale);
                    bundleGroup.addBundle(locale, PropertiesParser.parse(editor.getContent()));

                });
                bundleGroup.addChangeListener(new BundleChangeAdapter() {

                    @Override
                    public <T> void modify(BundleEvent<T> event) {
                        super.modify(event);
                        final Bundle bundle = (Bundle) event.data();
                        sourceEditors.get(bundle.getLocale()).setContent(PropertiesGenerator.generate(bundle));
                    }

                });

                this.keyTree = KeyTree.create(bundleGroup, keyTreeUpdater());

            } catch (IOException | CoreException e) {
                throwAsUnchecked(e);
            }
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println(elapsedTime);
            return null;
        }, executorService);

    }

    @SuppressWarnings("unchecked")
    public static <E extends Exception> void throwAsUnchecked(Exception exception) throws E {
        throw (E) exception;
    }

    private KeyTreeUpdater keyTreeUpdater() {
        if (PropertyPreferences.getInstance().isEditorTreeHierachical()) {
            return new GroupedKeyTreeUpdater(PropertyPreferences.getInstance().getKeyGroupSeparator());
        } else {
            return new FlatKeyTreeUpdater();
        }
    }

    /**
     * Gets bundle group.
     *
     * @return bundle group
     */
    @Override
    public BundleGroup getBundleGroup() {
        return bundleGroup;
    }

    /**
     * Gets all locales in this bundle.
     *
     * @return locales
     */
    @Override
    public List<Locale> getLocales() {
        return locales;
    }

    /**
     * Gets all locales sorted
     *
     * @returnlocales
     */
    @Override
    public List<Locale> getSortedLocales() {
        return UIUtils.sortLocales(locales);
    }

    /**
     * Gets the key tree for this bundle.
     *
     * @return key tree
     */
    @Override
    public KeyTree getKeyTree() {
        return keyTree;
    }

    /**
     * Gets the source editors.
     *
     * @return source editors.
     */
    @Override
    public List<SourceEditor> getSourceEditors() {
        return resourcesFactory.getSourceEditors();
    }

    /**
     * Save all dirty editors.
     *
     * @param monitor progress monitor
     */
    @Override
    public void save() {
        resourcesFactory.getSourceEditors().forEach(editor -> editor.saveDocument());
    }

    /**
     * Gets the multi-editor display name.
     *
     * @return display name
     */
    @Override
    public String getDisplayName() {
        return resourcesFactory.getDisplayName();
    }

    /**
     * Gets resource location
     *
     * @return resource location
     */
    @Override
    public String getResourceLocation() {
        return resourcesFactory.getResourceLocation();
    }

    /**
     * Returns whether a given file is known to the resource manager (i.e.,
     * if it is part of a resource bundle).
     *
     * @param file file to test
     * @return <code>true</code> if a known resource
     */
    public boolean isResource(IFile file) {
        Predicate<SourceEditor> knownFile = editor -> editor.getFile().equals(file);
        return resourcesFactory.getSourceEditors().stream().anyMatch(knownFile);
    }

    /**
     * Creates a properties file.
     *
     * @param locale file {@link Locale}
     * @return the newly created file
     * @throws CoreException problem creating file
     * @throws IOException problem creating file
     */
    @Override
    public IPropertyResource createPropertiesFile(Locale locale) throws CoreException, IOException {
        return resourcesFactory.getPropertiesFileCreator().createPropertyFile(locale, resourcesFactory.isExternal());
    }

    /**
     * Gets the source editor matching the given locale.
     *
     * @param locale locale matching requested source editor
     * @return source editor or <code>null</code> if no match
     */
    @Override
    public SourceEditor getSourceEditor(final Locale locale) {
        Log.d(TAG, "getSourceEditor() called with: locale = [" + locale + "]");
        return sourceEditors.get(locale);
    }

    /**
     * Add new source editor with given locale
     *
     * @param IPropertyResource of type file or Ifile
     * @param locale file {@link Locale}
     * @return SourceEditor
     */
    @Override
    public SourceEditor addSourceEditor(IPropertyResource fileDocument, Locale locale) {
        Log.d(TAG, "addSourceEditor() called with: fileDocument =  [" + fileDocument + "], locale = [" + locale + "]");
        SourceEditor sourceEditor = resourcesFactory.addResource(fileDocument, locale);
        sourceEditors.put(sourceEditor.getLocale(), sourceEditor);
        locales.add(locale);
        bundleGroup.addBundle(locale, PropertiesParser.parse(sourceEditor.getContent()));
        return sourceEditor;
    }

    /**
     * Reloads the properties files (parse them).
     */
    @Override
    public void reloadProperties() {
        resourcesFactory.getSourceEditors().stream().filter(isCacheDirty).forEach(editor -> {
            bundleGroup.addBundle(editor.getLocale(), PropertiesParser.parse(editor.getContent()));
            editor.resetCache();
        });
    }

    /**
     * Add new key
     *
     * @param key to add
     */
    @Override
    public void addNewKey(final String newKey) {
        Log.d(TAG, "addNewKey() called with: newKey =  [" + newKey + "]");
        keyTree.getBundleGroup().addBundleEntryKey(checkGroupSeparator(newKey));
    }

    /**
     * Remove key from tree list
     *
     * @param keyTreeItem to remove
     */
    @Override
    public void removeKey(final KeyTreeItem keyTreeItem) {
        Log.d(TAG, "renameKey() called with: keyTreeItem =  [" + keyTreeItem + "]");
        final List<KeyTreeItem> items = new ArrayList<>();
        items.add(keyTreeItem);
        items.addAll(keyTreeItem.getNestedChildren());
        items.forEach(item -> keyTree.getBundleGroup().removeBundleEntryKey(item.getId()));
    }

    /**
     * Rename key
     *
     * @param keyTreeItem
     * @param newKey name
     */
    @Override
    public void renameKey(final KeyTreeItem keyTreeItem, final String newKey) {
        Log.d(TAG, "renameKey() called with: keyTreeItem =  [" + keyTreeItem + "], newKey =[" + newKey + "]");
        final List<KeyTreeItem> items = new ArrayList<>();
        items.add(keyTreeItem);
        items.addAll(keyTreeItem.getNestedChildren());
        items.stream().filter(hasKeyTreeItem.apply(keyTreeItem)).forEach(item -> {
            String newItemKey = checkGroupSeparator(newKey) + item.getId().substring(keyTreeItem.getId().length());
            keyTree.getBundleGroup().renameBundleEntryKey(item.getId(), newItemKey);
        });
    }

    /**
     * Check group separator
     *
     * @param key
     * @return string
     */
    private String checkGroupSeparator(final String key) {
        final String separator = PropertyPreferences.getInstance().getKeyGroupSeparator();
        String changedKey = key;
        if (key.startsWith(separator)) {
            changedKey = key.substring(1);
        }
        if (key.endsWith(separator)) {
            changedKey = key.substring(1, key.length() - 1);
        }
        return changedKey;
    }

    /**
     * Check if key is in bundle group
     *
     * @return true if key is in bundle otherwise false
     */
    @Override
    public boolean containsKey(final String key) {
        return keyTree.getBundleGroup().containsKey(key);
    }

    /**
     * KeyTreeItem filter
     */
    private Function<KeyTreeItem, Predicate<KeyTreeItem>> hasKeyTreeItem = keyTreeItem -> item -> item.getId().startsWith(keyTreeItem.getId());

    /**
     * Checks if cache is dirty
     */
    private Predicate<SourceEditor> isCacheDirty = editor -> editor.isCacheDirty();
}
