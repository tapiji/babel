package org.eclipse.e4.babel.core.preference;


import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;


public final class PropertyPreferences {

    private static final String TAG = PropertyPreferences.class.getSimpleName();

    public static final int NEW_LINE_DEFAULT = 0;
    public static final int NEW_LINE_UNIX = 1;
    public static final int NEW_LINE_WIN = 2;
    public static final int NEW_LINE_MAC = 3;

    public static final String NODE_PATH = "org.eclipse.e4.babel.core";
    public static final String I18N_EDITOR_HEIGHT = "";

    public static final String WRAP_LINE_ENABLED = "STORE/WRAP_LINE_ENABLED";
    public static final String WRAP_LINE_CHAR_LIMIT = "STORE/WRAP_LINE_LIMIT";
    public static final String WRAP_ALIGN_EQUAL_SIGNS = "STORE/WRAP_ALIGN_EQUAL_SIGNS";
    public static final String WRAP_INDENT_LENGTH = "STORE/WRAP_INDENT_LENGTH";

    public static final String GROUP_LEVEL_DEPTH = "STORE/GROUP_LEVEL_DEPTH";
    public static final String GROUP_ALIGN_EQUALS_ENABLED = "STORE/GROUP_ALIGN_EQUALS_ENABLED";
    public static final String GROUP_KEYS_ENABLED = "STORE/GROUP_KEYS_ENABLED";

    public static final String CONVERT_TO_UNICODE = "STORE/CONVERT_TO_UNICODE";

    public static final String IS_GENERATED_BY_ENABLED = "STORE/SHOW_GENERATED_BY";

    private static final Preferences PREFERENCES = InstanceScope.INSTANCE.getNode(NODE_PATH);

    private PropertyPreferences() {
        super();
    }

    public int getI18nEditorHeight() {
        return PREFERENCES.getInt(I18N_EDITOR_HEIGHT, 80);
    }

    public void setI18nEditorHeight(final int editorHeight) {
        PREFERENCES.putInt(I18N_EDITOR_HEIGHT, editorHeight);
    }

    public boolean isWrapLineEnabled() {
        return PREFERENCES.getBoolean(WRAP_LINE_ENABLED, false);
    }

    public void isWrapLineEnabled(final boolean isWrapLineEnabled) {
        PREFERENCES.putBoolean(WRAP_LINE_ENABLED, isWrapLineEnabled);
    }

    public void setWrapLineCharLimit(final int lineCharLimit) {
        PREFERENCES.putInt(WRAP_LINE_CHAR_LIMIT, lineCharLimit);
    }

    public int getWrapLineCharLimit() {
        return PREFERENCES.getInt(WRAP_LINE_CHAR_LIMIT, 80);
    }

    public boolean isWrapAlignEqualSigns() {
        return PREFERENCES.getBoolean(WRAP_ALIGN_EQUAL_SIGNS, false);
    }

    public void isWrapAlignEqualSigns(final boolean wrapAlignEqualsSigns) {
        PREFERENCES.putBoolean(WRAP_ALIGN_EQUAL_SIGNS, wrapAlignEqualsSigns);
    }

    public static int getWrapIndentLength() {
        return PREFERENCES.getInt(WRAP_INDENT_LENGTH, 8);
    }

    public int getGroupLevelDepth() {
        return PREFERENCES.getInt(GROUP_LEVEL_DEPTH, 1);
    }

    public void setGroupLevelDepth(final int groupLevelDepth) {
        PREFERENCES.getInt(GROUP_LEVEL_DEPTH, groupLevelDepth);
    }

    public boolean isGroupAlignEqualsEnabled() {
        return PREFERENCES.getBoolean(GROUP_ALIGN_EQUALS_ENABLED, true);
    }

    public void isGroupAlignEqualsEnabled(final boolean isGroupAlignEqualsEnabled) {
        PREFERENCES.putBoolean(GROUP_ALIGN_EQUALS_ENABLED, isGroupAlignEqualsEnabled);
    }

    public boolean isConvertToUnicodeEnabled() {
        return PREFERENCES.getBoolean(CONVERT_TO_UNICODE, true);
    }

    public void isConvertToUnicodeEnabled(final boolean isConvertToUnicodeEnabled) {
        PREFERENCES.putBoolean(CONVERT_TO_UNICODE, isConvertToUnicodeEnabled);
    }

    public boolean isGroupKeysEnabled() {
        return PREFERENCES.getBoolean(GROUP_KEYS_ENABLED, true);
    }

    public void isGroupKeysEnabled(final boolean isGroupKeysEnabled) {
        PREFERENCES.putBoolean(GROUP_KEYS_ENABLED, isGroupKeysEnabled);
    }

    public boolean isGeneratedByEnabled() {
        return PREFERENCES.getBoolean(IS_GENERATED_BY_ENABLED, true);
    }

    public void isGeneratedByEnabled(final boolean isGeneratedByEnabled) {
        PREFERENCES.putBoolean(IS_GENERATED_BY_ENABLED, isGeneratedByEnabled);
    }

    private static class PropertyPreferencesHolder {

        private static final PropertyPreferences INSTANCE = new PropertyPreferences();
    }

    @Override
    public String toString() {
        return "PropertyPreferences [getI18nEditorHeight()=" + getI18nEditorHeight() + ", isWrapLineEnabled()=" + isWrapLineEnabled() + ", getWrapLineCharLimit()="
                        + getWrapLineCharLimit() + ", isWrapAlignEqualSigns()=" + isWrapAlignEqualSigns() + ", getGroupLevelDepth()=" + getGroupLevelDepth()
                        + ", isGroupAlignEqualsEnabled()=" + isGroupAlignEqualsEnabled() + ", isConvertToUnicodeEnabled()=" + isConvertToUnicodeEnabled() + "]";
    }

    public static PropertyPreferences getInstance() {
        return PropertyPreferencesHolder.INSTANCE;
    }
}
