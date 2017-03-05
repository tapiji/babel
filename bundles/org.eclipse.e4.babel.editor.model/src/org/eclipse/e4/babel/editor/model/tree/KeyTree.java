package org.eclipse.e4.babel.editor.model.tree;


import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.eclipse.e4.babel.editor.model.bundle.Bundle;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.eclipse.e4.babel.editor.model.bundle.BundleGroup;
import org.eclipse.e4.babel.editor.model.bundle.BundleObject;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleChangeAdapter;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.model.updater.KeyTreeUpdater;


/**
 * Tree representation of a bundle group.
 * 
 * @author Pascal Essiembre
 * @author cuhiodtick
 * @author Christian Behon
 */
public final class KeyTree extends BundleObject implements IKeyTreeVisitable {

    private static String TAG = KeyTree.class.getSimpleName();
    private final Map<String, KeyTreeItem> keyItemsCache = new TreeMap<String, KeyTreeItem>();
    private final Set<KeyTreeItem> rootKeyItems = new TreeSet<KeyTreeItem>();

    private String selectedKey;
    private KeyTreeUpdater keyTreeUpdater;
    private BundleGroup bundleGroup;

    private KeyTree(final BundleGroup bundleGroup, final KeyTreeUpdater keyTreeUpdater) {
        super();
        this.keyTreeUpdater = keyTreeUpdater;
        this.bundleGroup = bundleGroup;
        initBundleGroup(bundleGroup);
        loadKeys();
    }

    public static KeyTree create(final BundleGroup bundleGroup, final KeyTreeUpdater keyTreeUpdater) {
        return new KeyTree(bundleGroup, keyTreeUpdater);
    }

    private void initBundleGroup(final BundleGroup bundleGroup) {
        bundleGroup.addChangeListener(new BundleChangeAdapter() {

            @Override
            public <T> void add(final BundleEvent<T> event) {
                initBundle((Bundle) event.data());
            }
        });
        bundleGroup.getBundles().values().forEach(bundle -> initBundle(bundle));
    }

    protected void initBundle(final Bundle bundle) {
        bundle.addChangeListener(new BundleChangeAdapter() {

            @Override
            public <T> void add(final BundleEvent<T> event) {
                addKey(((BundleEntry) event.data()).getKey());
            }

            @Override
            public <T> void remove(final BundleEvent<T> event) {
                Collection<BundleEntry> entries = bundleGroup.getBundleEntries(((BundleEntry) event.data()).getKey());
                if (entries.size() == 0) {
                    removeKey(((BundleEntry) event.data()).getKey());
                }
            }

            @Override
            public <T> void modify(final BundleEvent<T> event) {
                modifyKey(((BundleEntry) event.data()).getKey());
            }
        });
    }

    public KeyTreeItem getKeyTreeItem(final String key) {
        return keyItemsCache.get(key);
    }

    public String getSelectedKey() {
        return selectedKey;
    }

    public Map<String, KeyTreeItem> getKeyItemsCache() {
        return keyItemsCache;
    }

    public Set<KeyTreeItem> getRootKeyItems() {
        return rootKeyItems;
    }

    public void addKey(final String key) {
        keyTreeUpdater.addKey(this, key);
        fireAdd(keyItemsCache.get(key));
    }

    public void removeKey(final String key) {
        if (keyItemsCache.get(key) != null) {
            keyTreeUpdater.removeKey(this, key);
            fireRemove(keyItemsCache.get(key));
        }
    }

    public void modifyKey(final String key) {
        fireModify(keyItemsCache.get(key));
    }

    public void selectKey(final String key) {
        if (key != null) {
            if (selectedKey == null || !selectedKey.equals(key)) {
                selectedKey = key;
                if (keyItemsCache.get(key) != null) {
                    fireSelect(keyItemsCache.get(key));
                }
            }
        }
    }

    public void setUpdater(final KeyTreeUpdater keyTreeUpdater) {
        this.keyTreeUpdater = keyTreeUpdater;
        this.keyItemsCache.clear();
        this.rootKeyItems.clear();
        loadKeys();
    }

    private void loadKeys() {
        bundleGroup.getKeys().forEach(key -> keyTreeUpdater.addKey(this, key));
        fireAdd(this);
    }

    public BundleGroup getBundleGroup() {
        return bundleGroup;
    }

    public void dispose() {
        keyItemsCache.clear();
        rootKeyItems.clear();
    }

    public KeyTreeUpdater getKeyTreeUpdater() {
        return keyTreeUpdater;
    }

    @Override
    public void accept(final IKeyTreeVisitor visitor, final Object passAlongArgument) {
        keyItemsCache.values().forEach(treeItem -> visitor.visitKeyTreeItem(treeItem, passAlongArgument));
        visitor.visitKeyTree(this, passAlongArgument);
    }
}
