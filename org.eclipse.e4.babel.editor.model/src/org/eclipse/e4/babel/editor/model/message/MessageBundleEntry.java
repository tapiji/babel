package org.eclipse.e4.babel.editor.model.message;


import java.util.Locale;


public final class MessageBundleEntry {

    private final String key;
    private final Locale locale;
    private final String comment;
    private final String text;
    private final boolean hasComment;

    private MessageBundleEntry(final String key, final String text, final String comment, final boolean hasComment) {
        super();
        this.key = (null == key) ? "" : key;
        this.locale = null;
        this.comment = comment;
        this.hasComment = hasComment;
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


    public boolean hasComment() {
        return hasComment;
    }

    @Override
    public String toString() {
        return "MessageBundleEntry [key=" + key + ", locale=" + locale + ", comment=" + comment + ", text=" + text + ", hasComment=" + hasComment + "]";
    }

    public static MessageBundleEntry createInstance(final String key, final String text, final String comment, final boolean hasComment) {
        return new MessageBundleEntry(key, text, comment, hasComment);
    }

}
