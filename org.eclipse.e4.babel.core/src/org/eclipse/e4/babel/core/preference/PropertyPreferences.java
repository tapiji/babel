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


    public static final String IS_GENERATED_BY_ENABLED = "isGeneratedByEnabled";

    public static final String SHOW_SUPPORT_ENABLED = "showSupportEnabled";


    public static final String ALIGN_EQUALS_ENABLED = "alignEqualsEnabled";
    public static final String SPACES_AROUND_EQUALS_ENABLED = "spacesAroundEqualsEnabled";

    // Group
    public static final String GROUP_KEYS_ENABLED = "groupKeysEnabled";
    public static final String GROUP_LEVEL_DEEP = "groupLevelDeep";
    public static final String GROUP_SEP_BLANK_LINE_COUNT = "groupSepBlankLineCount";
    public static final String GROUP_ALIGN_EQUALS_ENABLED = "groupAlignEqualsEnabled";
    public static final String GROUP_LEVEL_SEPARATOR = "groupLevelSeparator";

    // Wrap
    public static final String WRAP_LINES_ENABLED = "wrapLinesEnabled";
    public static final String WRAP_LINE_LENGTH = "wrapLineLength";
    public static final String WRAP_ALIGN_EQUALS_ENABLED = "wrapAlignEqualsEnabled";
    public static final String WRAP_INDENT_LENGTH = "wrapIndentLength";

    // Unicode
    public static final String UNICODE_ESCAPE_ENABLED = "unicodeEscapeEnabled";
    public static final String UNICODE_ESCAPE_UPPERCASE = "unicodeEscapeUppercase";
    public static final String UNICODE_UNESCAPE_ENABLED = "unicodeUnescapeEnabled";

    // Line
    public static final String FORCE_NEW_LINE_TYPE = "forceNewLineType";
    public static final String NEW_LINE_STYLE = "newLineStyle";
    public static final String NEW_LINE_NICE = "newLineNice";

    // Reporting

    //public static final String REPORT_DUPL_VALUES_ONLY_IN_ROOT_LOCALE = "reportDuplicateValuesOnlyInRootLocale";
    //

    private static final String REPORT_SIMILAR_VALUES_LEVENSTHEIN = "CORE_REPORT/SIMILAR_VALUES_LEVENSTHEIN";
    private static final String REPORT_MISSING_VALUES = "CORE_REPORT/MISSING_VALUES";
    private static final String REPORT_DUPLICATE_VALUES = "CORE_REPORT/DUPLICATE_VALUES";
    private static final String REPORT_SIMILAR_VALUES = "CORE_REPORT/REPORT_SIMILAR_VALUES_LEVEL";
    private static final String REPORT_SIMILAR_VALUES_WORD_COMPARE = "CORE_REPORT/SIMILAR_VALUES_WORD_COMPARE";
    private static final String REPORT_SIMILAR_VALUES_PRECISION = "CORE_REPORT/SIMILAR_VALUES_PRECISION";

    // General
    private static final String EDITOR_TREE_HIDDEN = "CORE_GENERAL/EDITOR_TREE_HIDDEN";
    private static final String EDITOR_TREE_HIERARCHICAL = "CORE_GENERAL/EDITOR_TREE_HIERARCHICAL";
    private static final String EDITOR_TREE_EXPANDED = "CORE_GENERAL/EDITOR_TREE_EXPANDED";
    private static final String FIELD_TAB_INSERTS = "CORE_GENERAL/FIELD_TAB_INSERTS";
    private static final String I18N_EDITOR_HEIGHT = "CORE_GENERAL/I18N_EDITOR_HEIGHT";
    private static final String KEY_GROUP_SEPARATOR = "CORE_GENERAL/KEY_GROUP_SEPARATOR";
    private static final String SUPPORT_NL = "CORE_GENERAL/SUPPORT_NL";
    private static final String SUPPORT_FRAGMENTS = "CORE_GENERAL/SUPPORT_FRAGMENTS";
    private static final String LOAD_ONLY_FRAGMENT_RESOURCES = "CORE_GENERAL/LOAD_ONLY_FRAGMENT_RESOURCES";
    private static final String CONVERT_ENCODED_TO_UNICODE = "CORE_GENERAL/CONVERT_ENCODED_TO_UNICODE";


    public static final String KEEP_EMPTY_FIELDS = "keepEmptyFields";
    public static final String SORT_KEYS = "sortKeys";
    public static final String DISPLAY_DEFAULT_COMMENT_FIELD = "displayCommentFieldNL";
    public static final String DISPLAY_LANG_COMMENT_FIELDS = "displayLangCommentFieldsNL";
    public static final String FILTER_LOCALES_STRING_MATCHERS = "localesFilterStringMatchers";
    public static final String ADD_MSG_EDITOR_BUILDER_TO_JAVA_PROJECTS = "addMsgEditorBuilderToJavaProjects";
    public static final String PROPERTIES_DISPLAYED_FILTER = "propertiesFilter";
    public static final String ENABLE_PROPERTIES_INDEXER = "enablePropertiesIndexer";


    private static final Preferences PREFERENCES = InstanceScope.INSTANCE.getNode(NODE_PATH);

    private PropertyPreferences() {
        super();
    }

    // General

    public boolean isConvertEncodedToUnicode() {
        return PREFERENCES.getBoolean(CONVERT_ENCODED_TO_UNICODE, true);
    }

    public void isConvertEncodedToUnicode(final boolean isConvertEncodedToUnicode) {
        PREFERENCES.putBoolean(CONVERT_ENCODED_TO_UNICODE, isConvertEncodedToUnicode);
    }

    public boolean isLoadOnlyFragmentResources() {
        return PREFERENCES.getBoolean(LOAD_ONLY_FRAGMENT_RESOURCES, false);
    }

    public void isLoadOnlyFragmentResources(final boolean isLoadOnlyFragmentResources) {
        PREFERENCES.putBoolean(LOAD_ONLY_FRAGMENT_RESOURCES, isLoadOnlyFragmentResources);
    }

    public boolean isSupportFragments() {
        return PREFERENCES.getBoolean(SUPPORT_FRAGMENTS, true);
    }

    public void isSupportFragments(final boolean isSupportFragments) {
        PREFERENCES.putBoolean(SUPPORT_FRAGMENTS, isSupportFragments);
    }

    public boolean isSUpportNl() {
        return PREFERENCES.getBoolean(SUPPORT_NL, false);
    }

    public void isSUpportNl(final boolean isSUpportNl) {
        PREFERENCES.putBoolean(SUPPORT_NL, isSUpportNl);
    }

    public String getKeyGroupSeparator() {
        return PREFERENCES.get(KEY_GROUP_SEPARATOR, ".");
    }

    public void setKeyGroupSeparator(final String seperator) {
        PREFERENCES.put(KEY_GROUP_SEPARATOR, seperator);
    }

    public int getI18nEditorHeight() {
        return PREFERENCES.getInt(I18N_EDITOR_HEIGHT, 80);
    }

    public void setI18nEditorHeight(final int editorHeight) {
        PREFERENCES.putInt(I18N_EDITOR_HEIGHT, editorHeight);
    }

    public boolean isFieldTabInsert() {
        return PREFERENCES.getBoolean(FIELD_TAB_INSERTS, false);
    }

    public void isFieldTabInsert(final boolean isFieldTabInsert) {
        PREFERENCES.putBoolean(FIELD_TAB_INSERTS, isFieldTabInsert);
    }

    public boolean isEditorTreeExpanded() {
        return PREFERENCES.getBoolean(EDITOR_TREE_EXPANDED, true);
    }

    public void isEditorTreeExpanded(final boolean isEditorTreeExpanded) {
        PREFERENCES.putBoolean(EDITOR_TREE_EXPANDED, isEditorTreeExpanded);
    }

    public boolean isEditorTreeHidden() {
        return PREFERENCES.getBoolean(EDITOR_TREE_HIDDEN, false);
    }

    public void isEditorTreeHidden(final boolean isEditorTreeHidden) {
        PREFERENCES.putBoolean(EDITOR_TREE_HIDDEN, isEditorTreeHidden);
    }

    public boolean isEditorTreeHierachical() {
        return PREFERENCES.getBoolean(EDITOR_TREE_HIERARCHICAL, true);
    }

    public void isEditorTreeHierachical(final boolean isEditorTreeHierachical) {
        PREFERENCES.putBoolean(EDITOR_TREE_HIERARCHICAL, isEditorTreeHierachical);
    }

    // Reporting
    public boolean isReportSimilarValues() {
        return PREFERENCES.getBoolean(REPORT_SIMILAR_VALUES, false);
    }

    public void isReportSimilarValues(final boolean isReportSimilarValues) {
        PREFERENCES.putBoolean(REPORT_SIMILAR_VALUES, isReportSimilarValues);
    }

    public boolean isReportMissingValues() {
        return PREFERENCES.getBoolean(REPORT_MISSING_VALUES, true);
    }

    public void isReportMissingValues(final boolean isReportMissingValue) {
        PREFERENCES.putBoolean(REPORT_MISSING_VALUES, isReportMissingValue);
    }

    public boolean isReportDuplicateValues() {
        return PREFERENCES.getBoolean(REPORT_DUPLICATE_VALUES, true);
    }

    public void isReportDuplicateValues(final boolean isReportDuplicateValues) {
        PREFERENCES.putBoolean(REPORT_DUPLICATE_VALUES, isReportDuplicateValues);
    }

    public boolean isReportSimilarValuesWordCompare() {
        return PREFERENCES.getBoolean(REPORT_SIMILAR_VALUES_WORD_COMPARE, true);
    }

    public void isReportSimilarValuesWordCompare(final boolean isReportSimValuesWordCompare) {
        PREFERENCES.putBoolean(REPORT_SIMILAR_VALUES_WORD_COMPARE, isReportSimValuesWordCompare);
    }

    public double getReportSimilarValuesPrecision() {
        return PREFERENCES.getDouble(REPORT_SIMILAR_VALUES_PRECISION, 0.75d);
    }

    public void setReportSimilarValuesPrecision(double reportSimValuesPrecision) {
        PREFERENCES.putDouble(REPORT_SIMILAR_VALUES_PRECISION, reportSimValuesPrecision);
    }

    public boolean isReportSimliarValuesLevensthein() {
        return PREFERENCES.getBoolean(REPORT_SIMILAR_VALUES_LEVENSTHEIN, false);
    }

    public void isReportSimliarValuesLevensthein(final boolean isSimliarValuesLevensthein) {
        PREFERENCES.putBoolean(REPORT_SIMILAR_VALUES_LEVENSTHEIN, isSimliarValuesLevensthein);
    }


    // NOT DONE

    public boolean isWrapLineEnabled() {
        return PREFERENCES.getBoolean(WRAP_LINES_ENABLED, true);
    }

    public void isWrapLineEnabled(final boolean isWrapLineEnabled) {
        PREFERENCES.putBoolean(WRAP_LINES_ENABLED, isWrapLineEnabled);
    }

    public boolean isGeneratedByEnabled() {
        return PREFERENCES.getBoolean(IS_GENERATED_BY_ENABLED, true);
    }

    public void isGeneratedByEnabled(final boolean isGeneratedByEnabled) {
        PREFERENCES.putBoolean(IS_GENERATED_BY_ENABLED, isGeneratedByEnabled);
    }


    public boolean isUnicodeEscapeEnabled() {
        return PREFERENCES.getBoolean(UNICODE_ESCAPE_ENABLED, true);
    }

    public void isUnicodeEscapeEnabled(final boolean isUnicodeEscapeEnabled) {
        PREFERENCES.putBoolean(UNICODE_ESCAPE_ENABLED, isUnicodeEscapeEnabled);
    }


    public static int getWrapIndentLength() {
        return PREFERENCES.getInt(WRAP_INDENT_LENGTH, 8);
    }

    public boolean isGroupAlignEqualsEnabled() {
        return PREFERENCES.getBoolean(GROUP_ALIGN_EQUALS_ENABLED, true);
    }

    public void isGroupAlignEqualsEnabled(final boolean isGroupAlignEqualsEnabled) {
        PREFERENCES.putBoolean(GROUP_ALIGN_EQUALS_ENABLED, isGroupAlignEqualsEnabled);
    }

    public boolean isGroupKeysEnabled() {
        return PREFERENCES.getBoolean(GROUP_KEYS_ENABLED, true);
    }

    public void isGroupKeysEnabled(final boolean isGroupKeysEnabled) {
        PREFERENCES.putBoolean(GROUP_KEYS_ENABLED, isGroupKeysEnabled);
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
        return "PropertyPreferences []";
    }

    public static PropertyPreferences getInstance() {
        return PropertyPreferencesHolder.INSTANCE;
    }

}
