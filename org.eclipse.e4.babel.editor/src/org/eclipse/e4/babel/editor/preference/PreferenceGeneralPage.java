package org.eclipse.e4.babel.editor.preference;


import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.preference.validator.NumberTextKeyListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class PreferenceGeneralPage extends APreferencePage {

    private static final String TAG = PreferenceGeneralPage.class.getSimpleName();
    private Text keyGroupSeparator;
    private Button convertEncodedToUnicode;
    private Button supportNL;
    private Button keyTreeHierarchical;
    private Button keyTreeExpanded;
    private Button fieldTabInserts;
    private Text i18nEditorHeight;
    private Button loadOnlyFragmentResources;
    private Button supportFragments;
    private Button editorTreeHidden;

    public PreferenceGeneralPage() {
        super("ResourceBundle Editor");
    }

    @Override
    protected Control createContents(final Composite parent) {
        final Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        keyGroupSeparator(composite);
        convertEncodedToUnicode(composite);
        supportNl(composite);
        supportFragments(composite);
        loadOnlyFragmentResources(composite);
        keyTreeExpanded(composite);
        keyTreeHierachical(composite);
        fieldTabInsert(composite);
        editorTreeHidden(composite);
        i18nEditorHeight(composite);

        initValues();
        return composite;
    }

    private void editorTreeHidden(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        editorTreeHidden = new Button(field, SWT.CHECK);
        createLabel(field, "Den Schlüsselbaum in Editor nicht anzeigen.");
    }

    private void supportFragments(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        supportFragments = new Button(field, SWT.CHECK);
        createLabel(field, "Laden von Ressourcen aus Fragment-Projekten unterstützen");
    }

    private void loadOnlyFragmentResources(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        loadOnlyFragmentResources = new Button(field, SWT.CHECK);
        createLabel(field, "Nur Ressourcen aus Fragment-Projekten laden (Host Plugin ignorieren)");
    }

    private void i18nEditorHeight(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        createLabel(field, "Minimale Editor-Höhe");
        i18nEditorHeight = new Text(field, SWT.BORDER);
        i18nEditorHeight.setTextLimit(3);
        setWidthInChars(i18nEditorHeight, 3);
        i18nEditorHeight.addKeyListener(new NumberTextKeyListener("Die 'Minimale Editor-Höhe' muss eine Zahl sein.", this));
    }

    private void fieldTabInsert(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        fieldTabInserts = new Button(field, SWT.CHECK);
        createLabel(field, "Die Tab-Taste f\u00FCgt einen Tab in die Lokalisierungstext ein.");
    }

    private void keyTreeExpanded(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        keyTreeExpanded = new Button(field, SWT.CHECK);
        createLabel(field, "Schl\u00FCsselbaum standardm\u00E4\u00DFig expandiert \u00F6ffnen.");
    }

    private void keyTreeHierachical(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        keyTreeHierarchical = new Button(field, SWT.CHECK);
        createLabel(field, "Schl\u00FCsselbaum standardm\u00E4\u00DFig als Hierarchie \u00F6ffnen.");
    }

    private void supportNl(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        supportNL = new Button(field, SWT.CHECK);
        createLabel(field, "Unterst\u00FCtzen der Eclipse \"nl\"-Struktur f\u00FCr PlugIn-Internationalisierung.");
    }

    private void convertEncodedToUnicode(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        convertEncodedToUnicode = new Button(field, SWT.CHECK);
        createLabel(field, "Umwandeln von \\uXXXX Werten in Unicode-Zeichen, wenn aus Properties-Datei gelesen wird.");
    }

    private void keyGroupSeparator(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        createLabel(field, "Schl\u00FCsselgruppenseparator:");
        keyGroupSeparator = new Text(field, SWT.BORDER);
        keyGroupSeparator.setTextLimit(2);
    }

    private void initValues() {
        keyGroupSeparator.setText(PropertyPreferences.getInstance().getKeyGroupSeparator());
        convertEncodedToUnicode.setSelection(PropertyPreferences.getInstance().isConvertEncodedToUnicode());
        supportNL.setSelection(PropertyPreferences.getInstance().isSUpportNl());
        keyTreeHierarchical.setSelection(PropertyPreferences.getInstance().isEditorTreeHierachical());
        keyTreeExpanded.setSelection(PropertyPreferences.getInstance().isEditorTreeExpanded());
        fieldTabInserts.setSelection(PropertyPreferences.getInstance().isFieldTabInsert());
        i18nEditorHeight.setText(String.valueOf(PropertyPreferences.getInstance().getI18nEditorHeight()));
        loadOnlyFragmentResources.setSelection(PropertyPreferences.getInstance().isLoadOnlyFragmentResources());
        supportFragments.setSelection(PropertyPreferences.getInstance().isSupportFragments());
        editorTreeHidden.setSelection(PropertyPreferences.getInstance().isEditorTreeHidden());
    }

    @Override
    public boolean performOk() {
        Log.d(TAG, "performOk");
        if (null != keyGroupSeparator) {
            PropertyPreferences.getInstance().setKeyGroupSeparator(keyGroupSeparator.getText());
        }
        if (null != i18nEditorHeight) {
            PropertyPreferences.getInstance().setI18nEditorHeight(Integer.valueOf(i18nEditorHeight.getText()));
        }
        if (null != fieldTabInserts) {
            PropertyPreferences.getInstance().isFieldTabInsert(fieldTabInserts.getSelection());
        }
        if (null != keyTreeExpanded) {
            PropertyPreferences.getInstance().isEditorTreeExpanded(keyTreeExpanded.getSelection());
        }
        if (null != editorTreeHidden) {
            PropertyPreferences.getInstance().isEditorTreeHidden(editorTreeHidden.getSelection());
        }
        if (null != keyTreeHierarchical) {
            PropertyPreferences.getInstance().isEditorTreeHierachical(keyTreeHierarchical.getSelection());
        }
        if (null != supportNL) {
            PropertyPreferences.getInstance().isSUpportNl(supportNL.getSelection());
        }
        if (null != supportFragments) {
            PropertyPreferences.getInstance().isSupportFragments(supportFragments.getSelection());
        }
        if (null != loadOnlyFragmentResources) {
            PropertyPreferences.getInstance().isLoadOnlyFragmentResources(loadOnlyFragmentResources.getSelection());
        }
        if (null != convertEncodedToUnicode) {
            PropertyPreferences.getInstance().isConvertEncodedToUnicode(convertEncodedToUnicode.getSelection());
        }
        return true;
    }

    @Override
    protected void performDefaults() {
        super.performDefaults();
        Log.d(TAG, "performDefaults");
        PropertyPreferences.getInstance().resetGeneralProperties();
        initValues();
    }
}
