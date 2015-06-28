package org.eclipse.e4.babel.editor.model.property;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class PropertyGroup {

    private final Map<Locale, PropertyFile> properties = new HashMap<>();
    private final Set<String> keys = new TreeSet<String>();

    public PropertyGroup() {


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
