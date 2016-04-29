package org.eclipse.e4.babel.editor.model.bundle;

import java.util.Locale;

public final class BundleEntry {

	private final String key;
	private final String value;
	private Locale locale;
	private final String comment;
	private final boolean commented;

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
		this(key, value, "", false);
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

	@Override
	public String toString() {
		return "BundleEntry [key=" + key + ", value=" + value + ", locale=" + locale + ", comment=" + comment
				+ ", commented=" + commented + "]";
	}

	public static BundleEntry create(final String key, final String value, final String comment,
			final boolean commented) {
		return new BundleEntry(key, value, comment, commented);
	}

	public static BundleEntry create(final String key, final String value, final String comment) {
		return new BundleEntry(key, value, comment);
	}

	public static BundleEntry create(final String key, final String value) {
		return new BundleEntry(key, value);
	}

}
