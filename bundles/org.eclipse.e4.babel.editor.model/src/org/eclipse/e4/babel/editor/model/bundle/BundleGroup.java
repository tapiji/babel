package org.eclipse.e4.babel.editor.model.bundle;


import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.eclipse.e4.babel.editor.model.bundle.visitor.IBundleVisitable;
import org.eclipse.e4.babel.editor.model.bundle.visitor.IBundleVisitor;
import org.eclipse.jdt.annotation.Nullable;


public final class BundleGroup extends BundleObject implements IBundleVisitable {

    private final Map<Locale, Bundle> bundles = new HashMap<>();
    private final SortedSet<String> keys = new TreeSet<String>();

    private BundleGroup() {
        super();
    }

    @Nullable
    public Bundle getBundle(final Locale locale) {
        return this.bundles.get(locale);
    }

    public Map<Locale, Bundle> getBundles() {
        return this.bundles;
    }

    public SortedSet<String> getKeys() {
        return this.keys;
    }

    public void addBundle(final Locale locale, final Bundle bundle) {
        final Bundle localBundle = bundles.get(locale);
        bundle.setLocale(locale);
        bundle.setGroup(this);
        if (localBundle == null) {
            bundles.put(locale, bundle);
            refreshKeys();
            fireAdd(bundle);
        } else {
            localBundle.copyFrom(bundle);
            refreshKeys();
            fireModify(bundle);
        }
    }

    public void addBundleEntry(final Locale locale, final BundleEntry bundleEntry) {
        final Bundle bundle = bundles.get(locale);
        if (bundle != null) {
            if (!bundleEntry.equals(getBundleEntry(locale, bundleEntry.getKey()))) {
                bundleEntry.setBundle(bundle);
                bundleEntry.setLocale(locale);
                bundle.addBundleEntry(bundleEntry);
                refreshKeys();
                fireModify(bundle);
            }
        }
    }

    public void addBundleEntryKey(final String key) {
        bundles.keySet().forEach(locale -> addBundleEntry(locale, BundleEntry.create(key)));
    }

    public void renameBundleEntryKey(final String oldKey, final String newKey) {
        bundles.keySet().forEach(locale -> {
            if (getBundleEntry(locale, oldKey) != null) {
                final Bundle bundle = getBundle(locale);
                if (bundle != null) {
                    bundle.renameBundleEntryKey(oldKey, newKey);
                    refreshKeys();
                    fireModify(bundle);
                }
            }
        });
    }

    public void copyBundleEntryKey(final String origKey, final String newKey) {
        if (!origKey.equals(newKey)) {
            bundles.keySet().forEach(locale -> {
                if (getBundleEntry(locale, origKey) != null) {
                    final Bundle bundle = getBundle(locale);
                    if (bundle != null) {
                        bundle.copyBundleEntry(origKey, newKey);
                        refreshKeys();
                    }
                }
            });
        }
    }

    public void removeBundleEntryKey(final String key) {
        bundles.keySet().forEach(locale -> {
            final BundleEntry entry = getBundleEntry(locale, key);
            if (entry != null) {
                final Bundle bundle = getBundle(locale);
                if (bundle != null) {
                    bundle.removeBundleEntry(entry);
                    refreshKeys();
                    fireModify(bundle);
                }
            }
        });
    }

    @Nullable
    public BundleEntry getBundleEntry(final Locale locale, final String key) {
        final Bundle bundle = bundles.get(locale);
        if (bundle != null) {
            return bundle.getBundleEntry(key);
        } else {
            return null;
        }
    }

    public Collection<BundleEntry> getBundleEntries(final String key) {
        return bundles.keySet().stream().map(locale -> getBundleEntry(locale, key)).filter(bundleEntry -> bundleEntry != null).collect(Collectors.toList());
    }

    public boolean containsKey(final String key) {
        return bundles.keySet().stream().filter(locale -> getBundleEntry(locale, key) != null).findFirst().isPresent();
    }

    public boolean containsLocale(final Locale value) {
        return bundles.values().stream().filter(locale -> locale.equals(value)).findFirst().isPresent();
    }

    public int getBundleCount() {
        return bundles.size();
    }

    public void refreshKeys() {
        keys.clear();
        bundles.values().forEach(bundle -> {
            keys.addAll(bundle.getKeys());
        });
    }

    public String getNextKey(final String currentKey) {
        boolean returnNextKey = false;
        for (String key : keys) {
            if (returnNextKey) {
                return key;
            }
            if (key.equals(currentKey)) {
                returnNextKey = true;
            }
        }
        return null;
    }

    @Nullable
    public String getPreviousKey(final String currentKey) {
        String previousKey = null;
        for (String key : keys) {
            if (key.equals(currentKey)) {
                return previousKey;
            }
            previousKey = key;
        }
        return null;
    }

    public void dispose() {
        this.bundles.clear();
        this.keys.clear();
    }

    public static BundleGroup create() {
        return new BundleGroup();
    }

    public boolean isKey(String key) {
        return getKeys().contains(key);
    }

    public int getSize() {
        return bundles.size();
    }

    @Override
    public void accept(final IBundleVisitor visitor, final Object passAlongArgument) {
        bundles.values().forEach(bundle -> {
            bundle.getBundleEntries().forEach(entry -> visitor.visitBundleEntry(entry, passAlongArgument));
            visitor.visitBundle(bundle, passAlongArgument);
        });
        visitor.visitBundleGroup(this, passAlongArgument);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bundles == null) ? 0 : bundles.hashCode());
        result = prime * result + ((keys == null) ? 0 : keys.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof BundleGroup)) return false;
        BundleGroup other = (BundleGroup) obj;
        if (bundles == null) {
            if (other.bundles != null) return false;
        } else if (!bundles.equals(other.bundles)) return false;
        if (keys == null) {
            if (other.keys != null) return false;
        } else if (!keys.equals(other.keys)) return false;
        return true;
    }
}
