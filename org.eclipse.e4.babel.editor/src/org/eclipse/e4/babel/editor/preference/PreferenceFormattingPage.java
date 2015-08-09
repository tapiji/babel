package org.eclipse.e4.babel.editor.preference;


import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.core.preference.PropertyPreferences.NewLineType;
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
import org.eclipselabs.e4.tapiji.logger.Log;


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
        ensureSpacesAroundEquals(composite);

        // Groups
        groupKeys(composite);
        groupLevelDeep(composite);
        linesBetweenGroups(composite);
        alignEqualSignsWithinGroups(composite);

        // Lines
        wrapLines(composite);
        wrapCharLimit(composite);
        spacesForIdent(composite);
        alignWrappedLinesWithEqualSign(composite);

        // Indent
        wrapNewLine(composite);
        newLineTypeForce(composite);
        keepEmptyFields(composite);
        sortKeys(composite);

        initValues();
        performRefresh();
        return composite;
    }

    private void showGeneratedBy(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        showGeneratedBy = new Button(field, SWT.CHECK);
        createLabel(field, "Zeige \"Erstellt von...\" Kopfzeilenkommentar (Zeige Deine Unterst\u00FCtzung!");
    }

    private void convertUnicodeToEncode(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        convertUnicodeToEncoded = new Button(field, SWT.CHECK);
        convertUnicodeToEncoded.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        createLabel(field, "Umwandeln Unicode-Wert nach \\uXXXX.");
    }

    private void newLineTypeForce(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        newLineTypeForce = new Button(field, SWT.CHECK);
        newLineTypeForce.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        final Composite newLineRadioGroup = new Composite(field, SWT.NONE);
        createLabel(newLineRadioGroup, "Erzwingen einer neuen Zeile mit Escape-Sequenz:");
        newLineRadioGroup.setLayout(new RowLayout());
        newLineTypes[NewLineType.UNIX.getId()] = new Button(newLineRadioGroup, SWT.RADIO);
        newLineTypes[NewLineType.UNIX.getId()].setText("UNIX (\\n)");
        newLineTypes[NewLineType.WIN.getId()] = new Button(newLineRadioGroup, SWT.RADIO);
        newLineTypes[NewLineType.WIN.getId()].setText("Windows (\\r\\n)");
        newLineTypes[NewLineType.MAC.getId()] = new Button(newLineRadioGroup, SWT.RADIO);
        newLineTypes[NewLineType.MAC.getId()].setText("Mac (\\r)");
    }

    private void ensureSpacesAroundEquals(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        ensureSpacesAroundEquals = new Button(field, SWT.CHECK);
        createLabel(field, "Mindestens ein Leerzeichen links und rechts eines Gleichheitszeichens lassen.");
    }

    private void sortKeys(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        sortKeys = new Button(field, SWT.CHECK);
        createLabel(field, "Sortiere die SChlüssel");
    }

    private void keepEmptyFields(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        keepEmptyFields = new Button(field, SWT.CHECK);
        createLabel(field, "Eintr\u00E4ge mit leeren Werten behalten.");
    }

    private void wrapNewLine(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        wrapNewLine = new Button(field, SWT.CHECK);
        createLabel(field, "Zeilen nach Escape-Sequenzzeichen umbrechen.");
    }

    private void spacesForIdent(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        createLabel(field, "Anzahl der Leerzeichen f\u00FCr die Einr\u00FCckung:");
        wrapIndentSpaces = new Text(field, SWT.BORDER);
        wrapIndentSpaces.setTextLimit(2);
        setWidthInChars(wrapIndentSpaces, 2);
        wrapIndentSpaces.addKeyListener(new NumberTextKeyListener("The 'How many spaces to use...' field must be numeric", this)); //$NON-NLS-1$

    }

    private void alignWrappedLinesWithEqualSign(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        wrapAlignEqualSigns = new Button(field, SWT.CHECK);
        wrapAlignEqualSigns.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        createLabel(field, "Ausrichten umgebrochener Zeilen mit gleichen Zeichen.");
    }

    private void wrapCharLimit(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        createLabel(field, "Anzahl der Zeichen, nach denen die Zeile umgebrochen werden soll:");
        wrapCharLimit = new Text(field, SWT.BORDER);
        wrapCharLimit.setTextLimit(4);
        setWidthInChars(wrapCharLimit, 4);
        wrapCharLimit.addKeyListener(new NumberTextKeyListener("The 'Wrap lines after...' field must be numeric", this));
    }

    private void wrapLines(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        wrapLines = new Button(field, SWT.CHECK);
        wrapLines.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        new Label(field, SWT.NONE).setText(" Lange Zeilen umbrechen.");
    }

    private void alignEqualSignsWithinGroups(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        groupAlignEqualSigns = new Button(field, SWT.CHECK);
        createLabel(field, "Ausrichten umgebrochener Zeilen mit gleichen Zeichen.");
    }

    private void linesBetweenGroups(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        createLabel(field, "Anzahl der Zeilen zwischen den Gruppen:");
        groupLineBreaks = new Text(field, SWT.BORDER);
        groupLineBreaks.setTextLimit(2);
        setWidthInChars(groupLineBreaks, 2);
        groupLineBreaks.addKeyListener(new NumberTextKeyListener("The 'How many level deep' field must be numeric", this));
    }

    private void groupLevelDeep(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        createLabel(field, "Baumtiefe:");
        groupLevelDeep = new Text(field, SWT.BORDER);
        groupLevelDeep.setText("1");
        groupLevelDeep.setTextLimit(2);
        setWidthInChars(groupLevelDeep, 2);
        groupLevelDeep.addKeyListener(new NumberTextKeyListener("The 'How many level deep' field must be numeric", this));

    }

    private void groupKeys(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        groupKeys = new Button(field, SWT.CHECK);
        groupKeys.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        createLabel(field, "Gruppenschl\u00FCssel.");
    }

    private void convertUnicodeUpperCase(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        convertUnicodeUpperCase = new Button(field, SWT.CHECK);
        createLabel(field, "Nutze Gro\u00DFbuchstaben f\u00FCr hexadezimale Zeichen.");
    }

    private void alignEqualSigns(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        alignEqualSigns = new Button(field, SWT.CHECK);
        alignEqualSigns.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        createLabel(field, "Ausrichten an Gleichheitszeichen.");
    }

    private void performRefresh() {

        final boolean isForceNewLineType = newLineTypeForce.getSelection();
        for (final Button newLineType : newLineTypes) {
            newLineType.setEnabled(isForceNewLineType);
        }

        final boolean isGroupKeys = groupKeys.getSelection();
        groupLevelDeep.setEnabled(isGroupKeys);
        groupLineBreaks.setEnabled(isGroupKeys);
        groupAlignEqualSigns.setEnabled(isGroupKeys);

        final boolean isWrapLines = wrapLines.getSelection();
        wrapCharLimit.setEnabled(isWrapLines);
        wrapIndentSpaces.setEnabled(isWrapLines);
        wrapAlignEqualSigns.setEnabled(isWrapLines);

        if (isWrapLines) {
            final boolean isWrapAlignEqualSigns = wrapAlignEqualSigns.getSelection();
            wrapIndentSpaces.setEnabled(!isWrapAlignEqualSigns);
        }
    }

    private void initValues() {
        final PropertyPreferences preferences = PropertyPreferences.getInstance();
        showGeneratedBy.setSelection(preferences.isGeneratedByEnabled());
        alignEqualSigns.setSelection(preferences.isAlignEqualSigns());

        convertUnicodeUpperCase.setSelection(preferences.isConvertUnicodeToEncodedUpper());
        convertUnicodeToEncoded.setSelection(preferences.isConvertUnicodedToEncoded());

        groupKeys.setSelection(preferences.isGroupKeys());
        groupLevelDeep.setText(String.valueOf(preferences.getGroupLevelDeep()));
        groupLineBreaks.setText(String.valueOf(preferences.getGroupLineBreaks()));
        groupAlignEqualSigns.setSelection(preferences.isAlignEqualSigns());

        wrapLines.setSelection(preferences.isWrapLines());
        wrapNewLine.setSelection(preferences.isWrapNewLine());
        wrapCharLimit.setText(String.valueOf(preferences.getWrapCharLimit()));
        wrapIndentSpaces.setText(String.valueOf(preferences.getWrapIndentSpace()));
        wrapAlignEqualSigns.setSelection(preferences.isWrapAlignEqualSign());
        sortKeys.setSelection(preferences.isSortKeys());

        newLineTypeForce.setSelection(preferences.isLineForced());
        newLineTypes[preferences.getLineType()].setSelection(true);
        ensureSpacesAroundEquals.setSelection(preferences.isSpaceAroundEqualsSigns());
        keepEmptyFields.setSelection(preferences.isKeepEmptyFields());
    }

    @Override
    public boolean performOk() {
        Log.d(TAG, "performOk");
        final PropertyPreferences preferences = PropertyPreferences.getInstance();
        if (null != showGeneratedBy) {
            preferences.isGeneratedByEnabled(showGeneratedBy.getSelection());
        }
        if (null != alignEqualSigns) {
            preferences.isAlignEqualSigns(alignEqualSigns.getSelection());
        }
        if (null != convertUnicodeUpperCase) {
            preferences.isConvertUnicodeToEncodedUpper(convertUnicodeUpperCase.getSelection());
        }
        if (null != convertUnicodeToEncoded) {
            preferences.isConvertUnicodedToEncoded(convertUnicodeToEncoded.getSelection());
        }
        if (null != groupKeys) {
            preferences.isGroupKeys(groupKeys.getSelection());
        }
        if (null != groupLevelDeep) {
            preferences.setGroupLevelDeep(Integer.valueOf(groupLevelDeep.getText()));
        }
        if (null != groupLineBreaks) {
            preferences.setGroupLineBreaks(Integer.valueOf(groupLineBreaks.getText()));
        }
        if (null != groupAlignEqualSigns) {
            preferences.isGroupAlignEqualSigns(groupAlignEqualSigns.getSelection());
        }
        if (null != wrapLines) {
            preferences.isWrapLines(wrapLines.getSelection());
        }
        if (null != wrapCharLimit) {
            preferences.setWrapCharLimit(Integer.valueOf(wrapCharLimit.getText()));
        }
        if (null != wrapIndentSpaces) {
            preferences.setWrapIndentSpace(Integer.valueOf(wrapIndentSpaces.getText()));
        }
        if (null != newLineTypeForce) {
            preferences.isLineForced(newLineTypeForce.getSelection());
        }
        if (null != sortKeys) {
            preferences.isSortKeys(sortKeys.getSelection());
        }
        if (null != ensureSpacesAroundEquals) {
            preferences.isSpaceAroundEqualsSigns(ensureSpacesAroundEquals.getSelection());
        }
        if (null != keepEmptyFields) {
            preferences.isKeepEmptyFields(keepEmptyFields.getSelection());
        }
        if (null != wrapNewLine) {
            preferences.isWrapNewLine(wrapNewLine.getSelection());
        }
        if (null != wrapAlignEqualSigns) {
            preferences.isWrapAlignEqualSign(wrapAlignEqualSigns.getSelection());
        }

        if (null != newLineTypes) {
            for (final NewLineType type : NewLineType.values()) {
                if (newLineTypes[type.getId()].getSelection()) {
                    preferences.setLineType(type);
                }
            }
        }
        return true;
    }

    @Override
    protected void performDefaults() {
        super.performDefaults();
        Log.d(TAG, "performDefaults");
        PropertyPreferences.getInstance().resetFormatProperties();
        initValues();
        performRefresh();
    }
}
