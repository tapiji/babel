package org.eclipse.e4.babel.editor.model.message;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.eclipse.jdt.annotation.Nullable;


public final class MessageBundle {

    private final Map<String, MessageBundleEntry> entries = new HashMap<>();
    private Locale locale;
    private String comment;


    public MessageBundle() {
        super();
    }


    public int getNumberOfEntries() {
        return entries.size();
    }

    @Nullable
    public MessageBundleEntry getEntry(final String key) {
        if (key != null) {
            return entries.get(key);
        } else {
            return null;
        }
    }

    public boolean addEntry(final MessageBundleEntry entry) {
        boolean isAdded = false;
        if (null != entry) {
            final MessageBundleEntry oldEntry = entries.get(entry.getKey());
            if (null == oldEntry) {
                entries.put(entry.getKey(), entry);
                isAdded = true;
            }
        }
        return isAdded;
    }

    public boolean removeEntry(final MessageBundleEntry entry) {
        boolean isRemoved = false;
        if (null != entry) {
            final MessageBundleEntry currentEntry = entries.get(entry.getKey());
            if (null != currentEntry) {
                entries.remove(currentEntry.getKey());
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    public static MessageBundle createInstance() {
        return new MessageBundle();
    }


    public void dispose() {
        entries.clear();
    }

    @Override
    public String toString() {
        return "MessageBundle [entries=" + entries + ", locale=" + locale + ", comment=" + comment + "]";
    }


    public String[] getKeys() {
        return null;
    }

}
