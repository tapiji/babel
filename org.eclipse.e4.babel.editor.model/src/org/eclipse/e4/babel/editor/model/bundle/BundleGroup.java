package org.eclipse.e4.babel.editor.model.bundle;


import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.eclipse.jdt.annotation.Nullable;


public class BundleGroup {

    private final Map<Locale, Bundle> bundles = new HashMap<>();
    private final SortedSet<String> keys = new TreeSet<String>();

    private BundleGroup() {
        super();
    }

    @Nullable
    public Bundle getBundle(final Locale locale) {
        return bundles.get(locale);
    }

    public Map<Locale, Bundle> getBundles() {
        return bundles;
    }

    public SortedSet<String> getKeys() {
        return keys;
    }

    public void addBundle(final Locale locale, final Bundle bundle) {
        bundle.setLocale(locale);
        bundle.setGroup(this);

        final Bundle localBundle = bundles.get(bundle);
        if (localBundle == null) {
            bundles.put(locale, bundle);
            refreshKeys();
        } else {
            //todo
        }
    }

    public void addBundleEntry(final Locale locale, final BundleEntry bundleEntry) {
        final Bundle bundle = bundles.get(locale);
        if (bundle != null) {
            final BundleEntry existingBundleEntry = getBundleEntry(locale, bundleEntry.getKey());
            if (existingBundleEntry != null && !existingBundleEntry.equals(bundleEntry)) {
                bundleEntry.setBundle(bundle);
                bundleEntry.setLocale(locale);
                bundle.addBundleEntry(bundleEntry);
                refreshKeys();
            }
        }
    }

    public void addBundleEntryKey(final String key) {
        bundles.keySet()
               .stream()
               .forEach(locale -> addBundleEntry(locale, BundleEntry.create(key)));
    }

    public void renameBundleEntryKey(final String newKey, final String oldKey) {
        bundles.keySet()
               .stream()
               .forEach(locale -> {
                   final BundleEntry entry = getBundleEntry(locale, oldKey);
                   if (entry != null) {
                       final Bundle bundle = getBundle(locale);
                       if (bundle != null) {
                           bundle.renameBundleEntryKey(newKey, oldKey);
                           refreshKeys();
                       }
                   }
               });
    }

    public void commentBundelEntryKey(final String key) {
        markBundleEntryAsComment(key, true);
    }

    public void uncommentBundleEntryKey(final String key) {
        markBundleEntryAsComment(key, false);
    }

    private void markBundleEntryAsComment(final String key, boolean comment) {
        bundles.keySet()
               .stream()
               .forEach(locale -> {
                   final BundleEntry entry = getBundleEntry(locale, key);
                   if (entry != null) {
                       final Bundle bundle = getBundle(locale);
                       if (bundle != null) {
                           if (comment) {
                               bundle.commentBundleEntry(key);
                           } else {
                               bundle.uncommentBundleEntry(key);
                           }
                       }
                   }
               });
    }

    public void copyBundleEntryKey(final String origKey, final String newKey) {
        if (origKey.equals(newKey)) {
            return;
        }
        bundles.keySet()
               .stream()
               .forEach(locale -> {
                   final BundleEntry origEntry = getBundleEntry(locale, origKey);
                   if (origEntry != null) {
                       final Bundle bundle = getBundle(locale);
                       if (bundle != null) {
                           bundle.copyBundleEntry(origKey, newKey);
                           refreshKeys();
                       }
                   }
               });
    }

    public void removeBundleEntryKey(final String key) {
        bundles.keySet()
               .stream()
               .forEach(locale -> {
                   final BundleEntry entry = getBundleEntry(locale, key);
                   if (entry != null) {
                       final Bundle bundle = getBundle(locale);
                       if (bundle != null) {
                           bundle.removeBundleEntry(entry);
                           refreshKeys();
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
        return bundles.keySet()
                      .stream()
                      .map(locale -> getBundleEntry(locale, key))
                      .filter(bundleEntry -> bundleEntry != null)
                      .collect(Collectors.toList());
    }

    public boolean containsKey(final String key) {
        return bundles.keySet()
                      .stream()
                      .filter(locale -> getBundleEntry(locale, key) != null)
                      .peek(action -> System.out.println(action))
                      .findFirst()
                      .isPresent();
    }

    public int getBundleCount() {
        return bundles.size();
    }

    public void refreshKeys() {
        keys.clear();
        bundles.values()
               .stream()
               .forEach(bundle -> {
                   keys.addAll(bundle.getKeys());
               });

    }

    public void getNextKey(final String key) {
        //return keys.stream().filter(key)
    }

    @Nullable
    public String getPreviousKey(final String currentKey) {
        return null;
        /*
         * return keys.stream()
         * .filter(key -> key.equals(currentKey))
         * .peek(action -> System.out.println(action))
         * .
         * .orElse(null);
         */
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
    public String toString() {

        return "BundleGroup [keys=" + keys + "]";
    }



}
