package org.eclipse.e4.babel.editor.model.property;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class PropertyFile {

    private static final Map<String, PropertyEntry> ENTRIES = new HashMap<>();
    private Locale locale;
    private String comment;


    public PropertyFile() {
        super();
    }


    @Override
    public String toString() {
        return "PropertyFile [locale=" + locale + ", comment=" + comment + "]";
    }
}
