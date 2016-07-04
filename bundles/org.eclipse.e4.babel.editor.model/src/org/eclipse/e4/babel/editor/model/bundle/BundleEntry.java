package org.eclipse.e4.babel.editor.model.bundle;


import java.util.Locale;
import org.eclipse.e4.babel.editor.model.bundle.visitor.IBundleVisitable;
import org.eclipse.e4.babel.editor.model.bundle.visitor.IBundleVisitor;


public final class BundleEntry implements IBundleVisitable {

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
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getComment() {
        return this.comment;
    }

    public boolean isCommented() {
        return this.commented;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public void setBundle(final Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return this.bundle;
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

    public static BundleEntry create(final String key) {
        return new BundleEntry(key);
    }

    @Override
    public void accept(final IBundleVisitor visitor, final Object passAlongArgument) {
        visitor.visitBundleEntry(this, passAlongArgument);
        visitor.visitBundle(this.bundle, passAlongArgument);
        if (this.bundle != null) {
            visitor.visitBundleGroup(this.bundle.getBundleGroup(), passAlongArgument);
        }
    }

    @Override
    public String toString() {
        return "BundleEntry [key=" + key + ", value=" + value + ", locale=" + locale + ", comment=" + comment + ", commented=" + commented +  "]";
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
