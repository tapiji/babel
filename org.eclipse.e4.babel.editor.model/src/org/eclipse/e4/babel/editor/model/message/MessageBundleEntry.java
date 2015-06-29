package org.eclipse.e4.babel.editor.model.message;


import java.util.Locale;


public final class MessageBundleEntry {

    private final String key;
    private final Locale locale;
    private final String comment;
    private final String text;
    private final boolean commented;

    private MessageBundleEntry(final String key, final String text, final String comment, final boolean commented) {
        super();
        this.key = (null == key) ? "" : key;
        this.locale = null;
        this.comment = comment;
        this.commented = commented;
        this.text = (null == text) ? "" : text;
    }


    public String getKey() {
        return key;
    }


    public Locale getLocale() {
        return locale;
    }


    public String getComment() {
        return comment;
    }


    public String getText() {
        return text;
    }


    public boolean isCommented() {
        return commented;
    }

    @Override
    public String toString() {
        return "PropertyEntry [key=" + key + ", locale=" + locale + ", comment=" + comment + ", text=" + text + ", commented=" + commented + "]";
    }

    public static MessageBundleEntry createInstance(final String key, final String text, final String comment, final boolean commented) {
        return new MessageBundleEntry(key, text, comment, commented);
    }

}
