package org.eclipse.e4.babel.editor.model.bundle;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.eclipse.e4.babel.editor.model.bundle.visitor.IBundleVisitable;
import org.eclipse.e4.babel.editor.model.bundle.visitor.IBundleVisitor;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;


public final class Bundle extends BundleObject implements IBundleVisitable {

    private final Map<String, BundleEntry> bundleEntries = new HashMap<>();
    private Locale locale;
    private String comment;
    private BundleGroup bundleGroup;

    private Bundle() {
        super();
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public int getBundleEntriesCount() {
        return this.bundleEntries.size();
    }

    public BundleGroup getBundleGroup() {
        return this.bundleGroup;
    }

    public void setBundleGroup(final BundleGroup bundleGroup) {
        this.bundleGroup = bundleGroup;
    }

    @Nullable
    public BundleEntry getBundleEntry(final String key) {
        return this.bundleEntries.get(key);
    }

    public void addBundleEntry(final BundleEntry bundleEntry) {
        BundleEntry oldBundleEntry = this.bundleEntries.get(bundleEntry.getKey());
        if (oldBundleEntry != null && !oldBundleEntry.equals(bundleEntry)) {
            this.bundleEntries.put(bundleEntry.getKey(), bundleEntry);
            bundleEntry.setLocale(locale);
            fireModify(oldBundleEntry);
        } else if (bundleEntry.getKey().trim().length() > 0) {
            this.bundleEntries.put(bundleEntry.getKey(), bundleEntry);
            bundleEntry.setLocale(locale);
            fireAdd(bundleEntry);
        }
    }

    public void removeBundleEntry(final BundleEntry bundleEntry) {
        if (bundleEntry != null) {
            BundleEntry bundleEntryToRemove = this.bundleEntries.get(bundleEntry.getKey());
            if (bundleEntryToRemove != null) {
                this.bundleEntries.remove(bundleEntryToRemove.getKey());
                fireRemove(bundleEntryToRemove);
            }
        }
    }

    public void removeBundleEntries(final List<String> bundleEntryKeys) {
        if (bundleEntryKeys != null && bundleEntryKeys.size() > 0) {
            bundleEntryKeys.stream().map(key -> this.bundleEntries.get(key)).filter(entry -> entry != null).collect(Collectors.toList()).forEach(entry -> removeBundleEntry(entry));
        }
    }

    public void renameBundleEntryKey(final String oldBundleEntryKey, final String newBundleEntryKey) {
        final BundleEntry oldBundleEntry = this.bundleEntries.get(oldBundleEntryKey);
        if (oldBundleEntry != null) {
            removeBundleEntry(oldBundleEntry);
            addBundleEntry(BundleEntry.create(newBundleEntryKey, oldBundleEntry.getValue(), oldBundleEntry.getComment(), oldBundleEntry.isCommented()));
        }
    }

    @NonNull
    public Set<String> getKeys() {
        final Set<String> keys = new TreeSet<String>();
        keys.addAll(this.bundleEntries.keySet());
        return keys;
    }

    public Collection<BundleEntry> getBundleEntries() {
        return this.bundleEntries.values();
    }

    public void copyBundleEntry(final String originalBundleEntryKey, final String newBundleEntryKey) {
        final BundleEntry bundleEntry = this.bundleEntries.get(originalBundleEntryKey);
        if (bundleEntry != null) {
            addBundleEntry(BundleEntry.create(newBundleEntryKey, bundleEntry.getValue(), bundleEntry.getComment()));
        }
    }

    @Override
    public void accept(final IBundleVisitor visitor, final Object passAlongArgument) {
        this.bundleEntries.values().forEach(entry -> visitor.visitBundleEntry(entry, passAlongArgument));
        visitor.visitBundle(this, passAlongArgument);
        visitor.visitBundleGroup(this.bundleGroup, passAlongArgument);
    }

    public void copyFrom(final Bundle bundle) {
        synchronized (getBundleEntries()) {
            bundle.getBundleEntries().stream().parallel().filter(entry -> bundle.getBundleEntry(entry.getKey()) == null).collect(Collectors.toList()).forEach(entry -> removeBundleEntry(entry));
        }
        bundle.getBundleEntries().stream().forEach(entry -> addBundleEntry(entry));
    }

    public static Bundle create() {
        return new Bundle();
    }

    public void setGroup(final BundleGroup bundleGroup) {
        this.bundleGroup = bundleGroup;
    }

    @Override
    public String toString() {
        return "Bundle [bundleEntries=" + bundleEntries.toString() + ", " + "locale=" + locale.toString() + ", comment=" + comment + ", bundleGroup=" + bundleGroup.toString() + "]";
    }
}
