package org.eclipse.e4.babel.editor.preference;


import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.preference.validator.NumberTextKeyListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public final class PreferenceFormattingPage extends APreferencePage {

    private static final String TAG = PreferenceFormattingPage.class.getSimpleName();

    private Button showGeneratedBy;

    private Button convertUnicodeToEncoded;
    private Button convertUnicodeUpperCase;

    private Button alignEqualSigns;
    private Button ensureSpacesAroundEquals;

    private Button groupKeys;
    private Text groupLevelDeep;
    private Text groupLineBreaks;
    private Button groupAlignEqualSigns;

    private Button wrapLines;
    private Text wrapCharLimit;
    private Button wrapAlignEqualSigns;
    private Text wrapIndentSpaces;
    private Button wrapNewLine;

    private Button newLineTypeForce;
    private final Button[] newLineTypes = new Button[3];

    private Button keepEmptyFields;
    private Button sortKeys;

    public PreferenceFormattingPage() {
        super("Formatieren");
    }

    @Override
    protected Control createContents(final Composite parent) {
        final Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        showGeneratedBy(composite);
        convertUnicodeToEncode(composite);
        convertUnicodeUpperCase(composite);
        alignEqualSigns(composite);

        // Groups
        groupKeys(composite);
        groupLevelDeep(composite);
        linesBetweenGroups(composite);
        alignEqualSignsWithinGroups(composite);
        ensureSpacesAroundEquals(composite);

        // Lines
        wrapLines(composite);
        wrapCharLimit(composite);
        wrapNewLine(composite);
        alignWrappedLinesWithEqualSign(composite);
        newLineTypeForce(composite);

        // Indent
        spacesForIdent(composite);
        keepEmptyFields(composite);
        sortKeys(composite);
        return composite;
    }

    private void showGeneratedBy(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        showGeneratedBy = new Button(field, SWT.CHECK);
        showGeneratedBy.setSelection(PropertyPreferences.getInstance().isGeneratedByEnabled());
        createLabel(field, "Zeige \"Erstellt von...\" Kopfzeilenkommentar (Zeige Deine Unterst\u00FCtzung!");
    }

    private void convertUnicodeToEncode(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        convertUnicodeToEncoded = new Button(field, SWT.CHECK);
        convertUnicodeToEncoded.setSelection(PropertyPreferences.getInstance().isConvertToUnicodeEnabled());
        convertUnicodeToEncoded.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        createLabel(field, "Umwandeln Unicode-Wert nach \\uXXXX.");
    }

    private void newLineTypeForce(final Composite composite) {
        // How should new lines appear in properties file
        final Composite field = createFieldComposite(composite);
        newLineTypeForce = new Button(field, SWT.CHECK);
        //newLineTypeForce.setSelection(prefs.getBoolean(MsgEditorPreferences.FORCE_NEW_LINE_TYPE));
        newLineTypeForce.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                // refreshEnabledStatuses();
            }
        });
        final Composite newLineRadioGroup = new Composite(field, SWT.NONE);
        new Label(newLineRadioGroup, SWT.NONE).setText("Force new line escape style:"); //$NON-NLS-1$
        newLineRadioGroup.setLayout(new RowLayout());
        newLineTypes[0] = new Button(newLineRadioGroup, SWT.RADIO);
        newLineTypes[0].setText("UNIX (\\n)"); //$NON-NLS-1$
        newLineTypes[1] = new Button(newLineRadioGroup, SWT.RADIO);
        newLineTypes[1].setText("Windows (\\r\\n)"); //$NON-NLS-1$
        newLineTypes[2] = new Button(newLineRadioGroup, SWT.RADIO);
        newLineTypes[2].setText("Mac (\\r)"); //$NON-NLS-1$
        //newLineTypes[prefs.getInt(MsgEditorPreferences.NEW_LINE_STYLE) - 1].setSelection(true);

    }

    private void ensureSpacesAroundEquals(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        new Label(field, SWT.NONE).setText("Align equal signs"); //$NON-NLS-1$
        ensureSpacesAroundEquals = new Button(field, SWT.CHECK);
        //ensureSpacesAroundEquals.setSelection(prefs.getBoolean(MsgEditorPreferences.SPACES_AROUND_EQUALS_ENABLED));
        new Label(field, SWT.NONE).setText("At least one space each side of equal signs");


    }

    private void sortKeys(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        sortKeys = new Button(field, SWT.CHECK);
        // sortKeys.setSelection(prefs.getBoolean(MsgEditorPreferences.SORT_KEYS));
        createLabel(field, "Sort keys");
    }

    private void keepEmptyFields(final Composite composite) {
        // Keep empty fields?
        final Composite field = createFieldComposite(composite);
        keepEmptyFields = new Button(field, SWT.CHECK);
        // keepEmptyFields.setSelection(PropertyPreferences.getInstance().);
        createLabel(field, "Keep keys without values");


    }

    private void wrapNewLine(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        wrapNewLine = new Button(field, SWT.CHECK);
        //wrapNewLine.setSelection(prefs.getBoolean(MsgEditorPreferences.NEW_LINE_NICE));
        new Label(field, SWT.NONE).setText("Wrap lines after escaped new line characters"); //$NON-NLS-1$
    }

    private void spacesForIdent(final Composite composite) {
        // How many spaces/tabs to use for indenting?
        final Composite field = createFieldComposite(composite, indentPixels);
        new Label(field, SWT.NONE).setText("How many spaces to use for indentation:"); //$NON-NLS-1$
        wrapIndentSpaces = new Text(field, SWT.BORDER);
        // wrapIndentSpaces.setText(prefs.getString(MsgEditorPreferences.WRAP_INDENT_LENGTH));
        wrapIndentSpaces.setTextLimit(2);
        setWidthInChars(wrapIndentSpaces, 2);
        wrapIndentSpaces.addKeyListener(new NumberTextKeyListener("The 'How many spaces to use...' field must be numeric", this)); //$NON-NLS-1$

    }

    private void alignWrappedLinesWithEqualSign(final Composite composite) {
        // Align wrapped lines with equal signs?
        final Composite field = createFieldComposite(composite, indentPixels);
        wrapAlignEqualSigns = new Button(field, SWT.CHECK);
        //wrapAlignEqualSigns.setSelection(prefs.getBoolean(MsgEditorPreferences.WRAP_ALIGN_EQUALS_ENABLED));
        wrapAlignEqualSigns.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        new Label(field, SWT.NONE).setText("Align wrapped lines with equal signs"); //$NON-NLS-1$
    }

    private void wrapCharLimit(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        createLabel(field, "Wrap lines after how many characters:");
        wrapCharLimit = new Text(field, SWT.BORDER);
        wrapCharLimit.setText(String.valueOf(PropertyPreferences.getInstance().getWrapLineCharLimit()));
        wrapCharLimit.setTextLimit(4);
        setWidthInChars(wrapCharLimit, 4);
        wrapCharLimit.addKeyListener(new NumberTextKeyListener("The 'Wrap lines after...' field must be numeric", this));
    }

    private void wrapLines(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        wrapLines = new Button(field, SWT.CHECK);
        wrapLines.setSelection(PropertyPreferences.getInstance().isWrapLineEnabled());
        wrapLines.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        new Label(field, SWT.NONE).setText("Wrap long lines"); //$NON-NLS-1$
    }

    private void alignEqualSignsWithinGroups(final Composite composite) {
        // Align equal signs within groups?
        final Composite field = createFieldComposite(composite, indentPixels);
        groupAlignEqualSigns = new Button(field, SWT.CHECK);
        // groupAlignEqualSigns.setSelection(prefs.getBoolean(MsgEditorPreferences.GROUP_ALIGN_EQUALS_ENABLED));
        new Label(field, SWT.NONE).setText("Align equal signs within groups"); //$NON-NLS-1$
    }

    private void linesBetweenGroups(final Composite composite) {
        // How many lines between groups?
        final Composite field = createFieldComposite(composite, indentPixels);
        new Label(field, SWT.NONE).setText("How many lines between groups:"); //$NON-NLS-1$
        groupLineBreaks = new Text(field, SWT.BORDER);
        //  groupLineBreaks.setText(prefs.getString(MsgEditorPreferences.GROUP_SEP_BLANK_LINE_COUNT));
        groupLineBreaks.setTextLimit(2);
        setWidthInChars(groupLineBreaks, 2);
        groupLineBreaks.addKeyListener(new NumberTextKeyListener("The 'How many level deep' field must be numeric", this)); //$NON-NLS-1$
    }

    private void groupLevelDeep(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        new Label(field, SWT.NONE).setText("How many level deep:");
        groupLevelDeep = new Text(field, SWT.BORDER);
        groupLevelDeep.setText(String.valueOf(PropertyPreferences.getInstance().getGroupLevelDepth()));
        groupLevelDeep.setTextLimit(2);
        setWidthInChars(groupLevelDeep, 2);
        groupLevelDeep.addKeyListener(new NumberTextKeyListener("The 'How many level deep' field must be numeric", this));
    }

    private void groupKeys(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        groupKeys = new Button(field, SWT.CHECK);
        // groupKeys.setSelection(prefs.getBoolean(MsgEditorPreferences.GROUP_KEYS_ENABLED));
        groupKeys.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        new Label(field, SWT.NONE).setText("Group keys");
    }

    private void convertUnicodeUpperCase(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        convertUnicodeUpperCase = new Button(field, SWT.CHECK);
        convertUnicodeUpperCase.setSelection(PropertyPreferences.getInstance().isUnicodeEscapeUppercase());
        new Label(field, SWT.NONE).setText("Use uppercase for hexadecimal letters");
    }

    private void alignEqualSigns(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        alignEqualSigns = new Button(field, SWT.CHECK);
        //alignEqualSigns.setSelection(prefs.getBoolean(MsgEditorPreferences.ALIGN_EQUALS_ENABLED));
        alignEqualSigns.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
    }

    private void performRefresh() {

    }

    @Override
    public boolean performOk() {
        return super.performOk();
    }

    @Override
    public boolean performCancel() {
        return super.performCancel();
    }

}
