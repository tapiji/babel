package org.eclipse.e4.babel.core.preference;


import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;


public final class PropertyPreferences {

    public enum NewLineType {
        DEFAULT(0),
        UNIX(1),
        WIN(2),
        MAC(3);

        private final int id;

        private NewLineType(final int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    // Report
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

    // Format
    private static final String IS_GENERATED_BY_ENABLED = "CORE_FORMAT/IS_GENERATED_BY_ENABLED";
    private static final String CONVERT_UNICODE_TO_ENCODED = "CORE_FORMAT/CONVERT_UNICODE_TO_ENCODED";
    private static final String CONVERT_UNICODE_TO_ENCODED_UPPER = "CORE_FORMAT/CONVERT_UNICODE_TO_ENCODED_UPPER";
    private static final String IS_WRAP_LINES = "CORE_FORMAT/IS_WRAP_LINES";
    private static final String WRAP_INDENT_LENGTH = "CORE_FORMAT/WRAP_INDENT_LENGTH";
    private static final String WRAP_LINE_LENGTH = "CORE_FORMAT/WRAP_LINE_LENGTH";
    private static final String WRAP_ALIGN_EQUALS_ENABLED = "CORE_FORMAT/WRAP_ALIGN_EQUALS_ENABLED";
    private static final String KEEP_EMPTY_FIELDS = "CORE_FORMAT/KEEP_EMPTY_FIELDS";
    private static final String IS_GROUP_KEYS = "CORE_FORMAT/IS_GROUP_KEYS";
    private static final String IS_ALIGN_EQUALS_SIGNS = "CORE_FORMAT/IS_ALIGN_EQUALS_SIGNS";
    private static final String IS_SPACES_AROUND_EQUALS_SIGNS = "CORE_FORMAT/IS_SPACES_AROUND_EQUALS_SIGNS";
    private static final String GROUP_LEVEL_DEEP = "CORE_FORMAT/GROUP_LEVEL_DEEP";
    private static final String IS_NEW_LINE_FORCED = "CORE_FORMAT/IS_NEW_LINE_NICE";
    private static final String NEW_LINE_TYPE = "CORE_FORMAT/NEW_LINE_TYPE";

    // NONE
    public static final String SORT_KEYS = "sortKeys";
    public static final String DISPLAY_DEFAULT_COMMENT_FIELD = "displayCommentFieldNL";
    public static final String DISPLAY_LANG_COMMENT_FIELDS = "displayLangCommentFieldsNL";
    public static final String FILTER_LOCALES_STRING_MATCHERS = "localesFilterStringMatchers";
    public static final String ADD_MSG_EDITOR_BUILDER_TO_JAVA_PROJECTS = "addMsgEditorBuilderToJavaProjects";
    public static final String PROPERTIES_DISPLAYED_FILTER = "propertiesFilter";
    public static final String ENABLE_PROPERTIES_INDEXER = "enablePropertiesIndexer";
    private static final String NODE_PATH = "org.eclipse.e4.babel.core";
    public static final String SHOW_SUPPORT_ENABLED = "showSupportEnabled";
    public static final String GROUP_SEP_BLANK_LINE_COUNT = "groupSepBlankLineCount";
    public static final String GROUP_ALIGN_EQUALS_ENABLED = "groupAlignEqualsEnabled";
    public static final String GROUP_LEVEL_SEPARATOR = "groupLevelSeparator";


    public static final String FORCE_NEW_LINE_TYPE = "forceNewLineType";


    private static final Preferences PREFERENCES = InstanceScope.INSTANCE.getNode(NODE_PATH);


    private PropertyPreferences() {
        super();
    }

    //=========================================================
    // FROMAT PREFERENCES
    //=========================================================

    public boolean isGeneratedByEnabled() {
        return PREFERENCES.getBoolean(IS_GENERATED_BY_ENABLED, true);
    }

    public void isGeneratedByEnabled(final boolean isGeneratedByEnabled) {
        PREFERENCES.putBoolean(IS_GENERATED_BY_ENABLED, isGeneratedByEnabled);
    }

    public boolean isUnicodeEscapeEnabled() {
        return PREFERENCES.getBoolean(CONVERT_UNICODE_TO_ENCODED, true);
    }

    public void isUnicodeEscapeEnabled(final boolean isUnicodeEscapeEnabled) {
        PREFERENCES.putBoolean(CONVERT_UNICODE_TO_ENCODED, isUnicodeEscapeEnabled);
    }

    public boolean isConvertUnicodeToEncodedUpper() {
        return PREFERENCES.getBoolean(CONVERT_UNICODE_TO_ENCODED_UPPER, true);
    }

    public void isConvertUnicodeToEncodedUpper(final boolean isUnicodeEscapeEnabled) {
        PREFERENCES.putBoolean(CONVERT_UNICODE_TO_ENCODED_UPPER, isUnicodeEscapeEnabled);
    }

    public boolean isWrapLines() {
        return PREFERENCES.getBoolean(IS_WRAP_LINES, false);
    }

    public void isWrapLines(final boolean isWrapLineEnabled) {
        PREFERENCES.putBoolean(IS_WRAP_LINES, isWrapLineEnabled);
    }

    public int getWrapIndentLength() {
        return PREFERENCES.getInt(WRAP_INDENT_LENGTH, 8);
    }

    public static void setWrapIndentLength(final int wrapIndentLength) {
        PREFERENCES.putInt(WRAP_INDENT_LENGTH, wrapIndentLength);
    }

    public boolean isKeepEmptyFields() {
        return PREFERENCES.getBoolean(KEEP_EMPTY_FIELDS, false);
    }

    public void isKeepEmptyFields(final boolean isKeepEmptyFields) {
        PREFERENCES.putBoolean(KEEP_EMPTY_FIELDS, isKeepEmptyFields);
    }

    public boolean isGroupKeys() {
        return PREFERENCES.getBoolean(IS_GROUP_KEYS, true);
    }

    public void isGroupKeys(final boolean isGroupKeys) {
        PREFERENCES.putBoolean(IS_GROUP_KEYS, isGroupKeys);
    }

    public boolean isAlignEqualSigns() {
        return PREFERENCES.getBoolean(IS_ALIGN_EQUALS_SIGNS, true);
    }

    public void isAlignEqualSigns(final boolean isAlignEqualSigns) {
        PREFERENCES.putBoolean(IS_ALIGN_EQUALS_SIGNS, isAlignEqualSigns);
    }

    public boolean isSpaceAroundEqualsSigns() {
        return PREFERENCES.getBoolean(IS_SPACES_AROUND_EQUALS_SIGNS, true);
    }

    public void isSpaceAroundEqualsSigns(final boolean isSpaceAroundEqualsSigns) {
        PREFERENCES.putBoolean(IS_SPACES_AROUND_EQUALS_SIGNS, isSpaceAroundEqualsSigns);
    }

    public String getGroupLevelDeep() {
        return PREFERENCES.get(GROUP_LEVEL_DEEP, "1");
    }

    public void setGroupLevelDeep(final String groupLevelDeepth) {
        PREFERENCES.put(GROUP_LEVEL_DEEP, groupLevelDeepth);
    }

    public boolean isWrapAlignEqualSign() {
        return PREFERENCES.getBoolean(WRAP_ALIGN_EQUALS_ENABLED, true);
    }

    public void isWrapAlignEqualSign(final boolean isWrapAlignEqualSign) {
        PREFERENCES.putBoolean(WRAP_ALIGN_EQUALS_ENABLED, isWrapAlignEqualSign);
    }

    public int getWrapLineLength() {
        return PREFERENCES.getInt(WRAP_LINE_LENGTH, 80);
    }

    public void setWrapLineLength(final int setWrapLineLength) {
        PREFERENCES.putInt(WRAP_LINE_LENGTH, setWrapLineLength);
    }

    public boolean isNewLineForced() {
        return PREFERENCES.getBoolean(IS_NEW_LINE_FORCED, true);
    }

    public void isNewLineNice(final boolean isNewLineNice) {
        PREFERENCES.putBoolean(IS_NEW_LINE_FORCED, isNewLineNice);
    }

    public int getNewLineType() {
        return PREFERENCES.getInt(NEW_LINE_TYPE, NewLineType.DEFAULT.getId());
    }

    public void setNewLineType(final NewLineType newLineType) {
        PREFERENCES.putInt(NEW_LINE_TYPE, newLineType.getId());
    }

    public void resetFormatProperties() {
        PREFERENCES.putBoolean(IS_GENERATED_BY_ENABLED, true);
        PREFERENCES.putBoolean(CONVERT_UNICODE_TO_ENCODED, true);
        PREFERENCES.putBoolean(CONVERT_UNICODE_TO_ENCODED_UPPER, true);
        PREFERENCES.putBoolean(IS_ALIGN_EQUALS_SIGNS, true);
        PREFERENCES.put(GROUP_LEVEL_DEEP, "1");
        PREFERENCES.putInt(NEW_LINE_TYPE, NewLineType.DEFAULT.getId());
        PREFERENCES.putInt(WRAP_LINE_LENGTH, 80);
        PREFERENCES.putBoolean(IS_SPACES_AROUND_EQUALS_SIGNS, true);
        PREFERENCES.putBoolean(KEEP_EMPTY_FIELDS, false);
        PREFERENCES.putBoolean(IS_WRAP_LINES, false);
        PREFERENCES.putInt(WRAP_INDENT_LENGTH, 8);
        PREFERENCES.putBoolean(IS_GROUP_KEYS, true);
        PREFERENCES.putBoolean(IS_NEW_LINE_FORCED, false);
    }

    //=========================================================
    // GENERAL PREFERENCES
    //=========================================================

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

    public void resetGeneralProperties() {
        PREFERENCES.putBoolean(CONVERT_ENCODED_TO_UNICODE, true);
        PREFERENCES.putBoolean(LOAD_ONLY_FRAGMENT_RESOURCES, false);
        PREFERENCES.putBoolean(SUPPORT_FRAGMENTS, true);
        PREFERENCES.put(KEY_GROUP_SEPARATOR, ".");
        PREFERENCES.putInt(I18N_EDITOR_HEIGHT, 80);
        PREFERENCES.putBoolean(FIELD_TAB_INSERTS, false);
        PREFERENCES.putBoolean(EDITOR_TREE_EXPANDED, true);
        PREFERENCES.putBoolean(EDITOR_TREE_HIDDEN, false);
        PREFERENCES.putBoolean(EDITOR_TREE_HIERARCHICAL, true);
        PREFERENCES.putBoolean(SUPPORT_NL, false);
    }

    //=========================================================
    // PERFORMANCE PREFERENCES
    //=========================================================

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

    public void resetPerformanceProperties() {
        PREFERENCES.putBoolean(REPORT_SIMILAR_VALUES, false);
        PREFERENCES.putBoolean(REPORT_MISSING_VALUES, true);
        PREFERENCES.putBoolean(REPORT_DUPLICATE_VALUES, true);
        PREFERENCES.putBoolean(REPORT_SIMILAR_VALUES_WORD_COMPARE, true);
        PREFERENCES.putBoolean(REPORT_SIMILAR_VALUES_LEVENSTHEIN, false);
        PREFERENCES.putDouble(REPORT_SIMILAR_VALUES_PRECISION, 0.75d);
    }


    public boolean clearPreferences() {
        boolean isNodeDeleted = false;
        try {
            PREFERENCES.clear();
            isNodeDeleted = true;
        } catch (BackingStoreException exception) {
            isNodeDeleted = false;
        }
        return isNodeDeleted;
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
