package org.eclipse.e4.babel.editor.model.bundle;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.e4.babel.editor.model.checks.visitor.DuplicateValuesVisitor;
import org.eclipse.e4.babel.editor.model.checks.visitor.SimilarValuesVisitor;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;


public final class Bundle extends BundleObject {

  private final Map<String, BundleEntry> bundleEntries = new HashMap<>();
  private Locale locale;
  private String comment;
  private BundleGroup bundleGroup;

  private Bundle() {
    super();
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(final String comment) {
    this.comment = comment;
  }

  public int getBundleEntriesCount() {
    return bundleEntries.size();
  }

  public BundleGroup getBundleGroup() {
    return bundleGroup;
  }

  public void setBundleGroup(final BundleGroup bundleGroup) {
    this.bundleGroup = bundleGroup;
  }

  @Nullable
  public BundleEntry getBundleEntry(final String key) {
    return bundleEntries.get(key);
  }

  public void addBundleEntry(final BundleEntry bundleEntry) {
    BundleEntry oldBundleEntry = bundleEntries.get(bundleEntry.getKey());
    if (oldBundleEntry != null && !oldBundleEntry.equals(bundleEntry)) {
      bundleEntries.put(bundleEntry.getKey(), bundleEntry);
      bundleEntry.setLocale(locale);
      fireModify(oldBundleEntry);
    } else if (bundleEntry.getKey()
                          .trim()
                          .length() > 0) {
      bundleEntries.put(bundleEntry.getKey(), bundleEntry);
      bundleEntry.setLocale(locale);
      fireAdd(bundleEntry);
    } else {
      // todo log not added message
    }
  }

  public void removeBundleEntry(final BundleEntry bundleEntry) {
    if (bundleEntry != null) {
      BundleEntry bundleEntryToRemove = bundleEntries.get(bundleEntry.getKey());
      if (bundleEntryToRemove != null) {
        bundleEntries.remove(bundleEntryToRemove.getKey());
        fireRemove(bundleEntryToRemove);
      }
    }
  }

  public void removeBundleEntries(final List<String> bundleEntryKeys) {
    if (bundleEntryKeys != null && bundleEntryKeys.size() > 0) {
      bundleEntryKeys.stream()
                     .map(key -> bundleEntries.get(key))
                     .filter(entry -> entry != null)
                     .collect(Collectors.toList())
                     .forEach(entry -> removeBundleEntry(entry));
    }
  }

  public void renameBundleEntryKey(final String oldBundleEntryKey, final String newBundleEntryKey) {
    final BundleEntry oldBundleEntry = bundleEntries.get(oldBundleEntryKey);
    if (oldBundleEntry != null) {
      removeBundleEntry(oldBundleEntry);
      addBundleEntry(BundleEntry.create(newBundleEntryKey, oldBundleEntry.getValue(), oldBundleEntry.getComment(), oldBundleEntry.isCommented()));
    }
  }

  @NonNull
  public Set<String> getKeys() {
    final Set<String> keys = new TreeSet<String>();
    keys.addAll(bundleEntries.keySet());
    return keys;
  }

  public Collection<BundleEntry> getBundleEntries() {
    return bundleEntries.values();
  }

  public void copyBundleEntry(final String originalBundleEntryKey, final String newBundleEntryKey) {
    final BundleEntry bundleEntry = bundleEntries.get(originalBundleEntryKey);
    if (bundleEntry != null) {
      addBundleEntry(BundleEntry.create(newBundleEntryKey, bundleEntry.getValue(), bundleEntry.getComment()));
    }
  }

  public void copyFrom(final Bundle bundle) {
    bundle.getBundleEntries()
          .stream()
          .filter(entry -> bundle.getBundleEntry(entry.getKey()) == null)
          .collect(Collectors.toList())
          .forEach(entry -> removeBundleEntry(entry));

    bundle.getBundleEntries()
          .stream()
          .forEach(entry -> addBundleEntry(entry));
  }

  public static Bundle create() {
    return new Bundle();
  }

  public void setGroup(BundleGroup bundleGroup) {
    this.bundleGroup = bundleGroup;
  }

  @Override
  public String toString() {
    return "Bundle [locale=" + locale + ", comment=" + comment + "]";
  }

	public void accept(SimilarValuesVisitor similarVisitor, BundleEntry bundleEntry) {
	// TODO Auto-generated method stub
	
	}

	public void accept(DuplicateValuesVisitor duplVisitor, BundleEntry bundleEntry) {
		// TODO Auto-generated method stub
		
	}
}