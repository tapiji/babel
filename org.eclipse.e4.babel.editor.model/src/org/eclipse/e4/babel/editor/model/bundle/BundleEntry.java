package org.eclipse.e4.babel.editor.model.bundle;


import java.util.Locale;


public final class BundleEntry {

  private final String key;
  private final String value;
  private Locale locale;
  private final String comment;
  private final boolean commented;
  private Bundle bundle;

  private BundleEntry(final String key, final String value, final String comment, final boolean commented) {
    super();
    this.key = (null == key) ? "" : key;
    this.locale = null;
    this.comment = comment == null ? "" : comment;
    this.commented = commented;
    this.value = (null == value) ? "" : value;
  }

  private BundleEntry(final String key, final String value, final String comment) {
    this(key, value, comment, false);
  }

  private BundleEntry(final String key, final String value) {
    this(key, value, null, false);
  }

  private BundleEntry(final String key) {
    this(key, null, null, false);
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getComment() {
    return comment;
  }

  public boolean isCommented() {
    return commented;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setBundle(Bundle bundle) {
    this.bundle = bundle;
  }

  public Bundle getBundle() {
    return bundle;
  }

  public static BundleEntry create(final String key, final String value, final String comment, final boolean commented) {
    return new BundleEntry(key, value, comment, commented);
  }

  public static BundleEntry create(final String key, final String value, final String comment) {
    return new BundleEntry(key, value, comment);
  }

  public static BundleEntry create(final String key, final String value) {
    return new BundleEntry(key, value);
  }

  public static BundleEntry create(String key) {
    return new BundleEntry(key);
  }

  @Override
  public String toString() {
    return "BundleEntry [key=" + key + ", value=" + value + ", locale=" + locale + ", comment=" + comment + ", commented=" + commented + ", bundle=" + bundle + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
    result = prime * result + ((comment == null) ? 0 : comment.hashCode());
    result = prime * result + (commented ? 1231 : 1237);
    result = prime * result + ((key == null) ? 0 : key.hashCode());
    result = prime * result + ((locale == null) ? 0 : locale.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof BundleEntry)) return false;
    BundleEntry other = (BundleEntry) obj;
    if (bundle == null) {
      if (other.bundle != null) return false;
    } else if (!bundle.equals(other.bundle)) return false;
    if (comment == null) {
      if (other.comment != null) return false;
    } else if (!comment.equals(other.comment)) return false;
    if (commented != other.commented) return false;
    if (key == null) {
      if (other.key != null) return false;
    } else if (!key.equals(other.key)) return false;
    if (locale == null) {
      if (other.locale != null) return false;
    } else if (!locale.equals(other.locale)) return false;
    if (value == null) {
      if (other.value != null) return false;
    } else if (!value.equals(other.value)) return false;
    return true;
  }
}
