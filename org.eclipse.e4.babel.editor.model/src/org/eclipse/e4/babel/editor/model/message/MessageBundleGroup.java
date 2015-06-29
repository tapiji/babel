package org.eclipse.e4.babel.editor.model.message;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class MessageBundleGroup {

    private final Map<Locale, MessageBundle> properties = new HashMap<>();
    private final Set<String> keys = new TreeSet<String>();

    public MessageBundleGroup() {


    }


    public void dispose() {
        if (null != properties) {
            properties.clear();
        }
        if (null != keys) {
            keys.clear();
        }

    }
}
