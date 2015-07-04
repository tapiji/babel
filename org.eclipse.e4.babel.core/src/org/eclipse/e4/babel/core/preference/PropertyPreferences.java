package org.eclipse.e4.babel.core.preference;


import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;


public final class PropertyPreferences {

    private static final String TAG = PropertyPreferences.class.getSimpleName();

    private static final int NEW_LINE_DEFAULT = 0;
    private static final int NEW_LINE_UNIX = 1;
    private static final int NEW_LINE_WIN = 2;
    private static final int NEW_LINE_MAC = 3;

    private static final String NODE_PATH = "org.eclipse.e4.babel.core";
    private static final String I18N_EDITOR_HEIGHT = "";

    private static final String IS_GENERATED_BY_ENABLED = "STORE/SHOW_GENERATED_BY";

    // Groups
    public static final String GROUP_KEYS_ENABLED = "CORE_GROUP/KEYSENABLED";
    public static final String GROUP_LEVEL_DEEP = "CORE_GROUP/LEVELDEEP";
    public static final String GROUP_SEP_BLANK_LINE_COUNT = "CORE_GROUP/SEPBLANKLINECOUNT";
    public static final String GROUP_ALIGN_EQUALS_ENABLED = "CORE_GROUP/ALIGNEQUALSENABLED";

    // Unicode
    public static final String UNICODE_ESCAPE_ENABLED = "CORE_UNICODE/ESCAPE_ENABLED";
    public static final String UNICODE_ESCAPE_UPPERCASE = "CORE_UNICODE/ESCAPE_UPPERCASE";
    public static final String UNICODE_UNESCAPE_ENABLED = "CORE_UNICODE/UNESCAPE_ENABLED";

    // Wrapping
    public static final String WRAP_LINES_ENABLED = "CORE_WRAP/LINES_ENABLED";
    public static final String WRAP_LINE_LENGTH = "CORE_WRAP/LINE_LENGTH";
    public static final String WRAP_ALIGN_EQUALS_ENABLED = "CORE_WRAP/ALIGNE_QUALS_ENABLED";
    public static final String WRAP_INDENT_LENGTH = "CORE_WRAP/INDENT_LENGTH";

    // Reporting
    public static final String REPORT_MISSING_VALUES_LEVEL = "CORE_REPORT/DETECT_MISSING_VALUES_LEVEL";
    public static final String REPORT_DUPL_VALUES_LEVEL = "CORE_REPORT/DUPLICATE_VALUES_LEVEL";
    public static final String REPORT_DUPL_VALUES_ONLY_IN_ROOT_LOCALE = "CORE_REPORT/DUPLICATE_VALUES_ONLY_IN_ROOT_LOCALE";
    public static final String REPORT_SIM_VALUES_LEVEL = "CORE_REPORT/SIMILAR_VALUES_LEVEL";
    public static final String REPORT_SIM_VALUES_WORD_COMPARE = "CORE_REPORT/SIMILAR_VALUES_WORD_COMPARE";
    public static final String REPORT_SIM_VALUES_LEVENSTHEIN = "CORE_REPORT/SIMILAR_VALUES_LEVENSTHEIN";
    public static final String REPORT_SIM_VALUES_PRECISION = "CORE_REPORT/SIMILAR_VALUES_PRECISION";


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

    public boolean isUnicodeEscapeUppercase() {
        return PREFERENCES.getBoolean(UNICODE_ESCAPE_UPPERCASE, true);
    }

    public void isUnicodeEscapeUppercase(final boolean isGeneratedByEnabled) {
        PREFERENCES.putBoolean(UNICODE_ESCAPE_UPPERCASE, isGeneratedByEnabled);
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
