package org.eclipse.e4.babel.editor.model.message;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.eclipse.jdt.annotation.Nullable;


public class MessageBundle {

    private static final Map<String, MessageBundleEntry> ENTRIES = new HashMap<>();
    private Locale locale;
    private String comment;


    public MessageBundle() {
        super();
    }


    public int getNumberOfEntries() {
        return ENTRIES.size();
    }

    @Nullable
    public MessageBundleEntry getEntry(final String key) {
        if (key != null) {
            return ENTRIES.get(key);
        } else {
            return null;
        }
    }

    public boolean addEntry(final MessageBundleEntry entry) {
        boolean isAdded = false;
        if (null != entry) {
            final MessageBundleEntry oldEntry = ENTRIES.get(entry.getKey());
            if (null == oldEntry) {
                ENTRIES.put(entry.getKey(), entry);
                isAdded = true;
            }
        }
        return isAdded;
    }

    public boolean removeEntry(final MessageBundleEntry entry) {
        boolean isRemoved = false;
        if (null != entry) {
            final MessageBundleEntry currentEntry = ENTRIES.get(entry.getKey());
            if (null != currentEntry) {
                ENTRIES.remove(currentEntry.getKey());
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    public static MessageBundle createInstance() {
        return new MessageBundle();
    }


    public void dispose() {
        ENTRIES.clear();
    }

    @Override
    public String toString() {
        return "PropertyFile [locale=" + locale + ", comment=" + comment + "]";
    }

}
