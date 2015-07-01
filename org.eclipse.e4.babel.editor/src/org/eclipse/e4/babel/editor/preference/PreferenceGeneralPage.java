package org.eclipse.e4.babel.editor.preference;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public final class PreferenceGeneralPage extends APreferencePage {

    private Text keyGroupSeparator;
    private Text filterLocales;
    private Button convertEncodedToUnicode;
    private Button supportNL;
    private Button keyTreeHierarchical;
    private Button keyTreeExpanded;
    private Button fieldTabInserts;
    private Button setupRbeNatureAutomatically;

    public PreferenceGeneralPage() {
        super("ResourceBundle Editor");

    }

    @Override
    protected Control createContents(Composite parent) {
        Composite field = null;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        // Key group separator
        field = createFieldComposite(composite);
        new Label(field, SWT.NONE).setText("Schl\u00FCsselgruppenseparator:"); //$NON-NLS-1$
        keyGroupSeparator = new Text(field, SWT.BORDER);
        //keyGroupSeparator.setText(prefs.getSerializerConfig().getGroupLevelSeparator());
        // prefs.getString(MsgEditorPreferences.GROUP__LEVEL_SEPARATOR));
        keyGroupSeparator.setTextLimit(2);

        field = createFieldComposite(composite);
        Label filterLocalesLabel = new Label(field, SWT.NONE);
        filterLocalesLabel.setText("Displayed locales:"); //$NON-NLS-1$
        //filterLocalesLabel.setToolTipText(MessagesEditorPlugin.getString("prefs.filterLocales.tooltip")); //$NON-NLS-1$
        filterLocales = new Text(field, SWT.BORDER);
        //   filterLocales.setText(prefs.getFilterLocalesStringMatcher());
        // prefs.getString(MsgEditorPreferences.GROUP__LEVEL_SEPARATOR));
        filterLocales.setTextLimit(22);
        setWidthInChars(filterLocales, 16);

        // Convert encoded to unicode?
        field = createFieldComposite(composite);
        convertEncodedToUnicode = new Button(field, SWT.CHECK);
        //   convertEncodedToUnicode.setSelection(prefs.getSerializerConfig().isUnicodeEscapeEnabled());
        // prefs.getBoolean(MsgEditorPreferences.CONVERT_ENCODED_TO_UNICODE));
        new Label(field, SWT.NONE).setText("Umwandeln von \\uXXXX Werten in Unicode-Zeichen, wenn aus Properties-Datei gelesen wird."); //$NON-NLS-1$

        // Support "NL" localization structure
        field = createFieldComposite(composite);
        supportNL = new Button(field, SWT.CHECK);
        //   supportNL.setSelection(prefs.isNLSupportEnabled());
        // prefs.getBoolean(MsgEditorPreferences.SUPPORT_NL));
        new Label(field, SWT.NONE).setText("Unterst\u00FCtzen der Eclipse \"nl\"-Struktur f\u00FCr PlugIn-Internationalisierung."); //$NON-NLS-1$

        // Setup rbe validation builder on java projects automatically.
        field = createFieldComposite(composite);
        setupRbeNatureAutomatically = new Button(field, SWT.CHECK);
        //  setupRbeNatureAutomatically.setSelection(prefs.isBuilderSetupAutomatically());
        // prefs.getBoolean(MsgEditorPreferences.SUPPORT_NL));
        // new Label(field, SWT.NONE).setText(MessagesEditorPlugin.getString("prefs.setupValidationBuilderAutomatically")); //$NON-NLS-1$


        // Default key tree mode (tree vs flat)
        field = createFieldComposite(composite);
        keyTreeHierarchical = new Button(field, SWT.CHECK);
        // keyTreeHierarchical.setSelection(prefs.isKeyTreeHierarchical());
        // prefs.getBoolean(MsgEditorPreferences.KEY_TREE_HIERARCHICAL));
        new Label(field, SWT.NONE).setText("Schl\u00FCsselbaum standardm\u00E4\u00DFig als Hierarchie \u00F6ffnen.");//$NON-NLS-1$

        // Default key tree expand status (expanded vs collapsed)
        field = createFieldComposite(composite);
        keyTreeExpanded = new Button(field, SWT.CHECK);
        //  keyTreeExpanded.setSelection(prefs.isKeyTreeExpanded());
        //                prefs.getBoolean(MsgEditorPreferences.KEY_TREE_EXPANDED)); //$NON-NLS-1$
        new Label(field, SWT.NONE).setText("Schl\u00FCsselbaum standardm\u00E4\u00DFig expandiert \u00F6ffnen."); //$NON-NLS-1$

        // Default tab key behaviour in text field
        field = createFieldComposite(composite);
        fieldTabInserts = new Button(field, SWT.CHECK);
        //   fieldTabInserts.setSelection(prefs.isFieldTabInserts());
        // prefs.getBoolean(MsgEditorPreferences.FIELD_TAB_INSERTS));
        new Label(field, SWT.NONE).setText("Die Tab-Taste f\u00FCgt einen Tab in die Lokalisierungstext ein."); //$NON-NLS-1$


        return composite;
    }
}
