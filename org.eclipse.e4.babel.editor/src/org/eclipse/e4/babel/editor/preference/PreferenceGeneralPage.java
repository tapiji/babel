package org.eclipse.e4.babel.editor.preference;


import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.preference.validator.NumberTextKeyListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;


public final class PreferenceGeneralPage extends APreferencePage {

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
        keyTreeHierachical(composite);
        keyTreeExpanded(composite);
        fieldTabInsert(composite);
        loadOnlyFragmentResources(composite);
        supportFragments(composite);
        editorTreeHidden(composite);
        i18nEditorHeight(composite);

        return composite;
    }

    private void editorTreeHidden(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        editorTreeHidden = new Button(field, SWT.CHECK);
        editorTreeHidden.setSelection(PropertyPreferences.getInstance().isEditorTreeHidden());
        createLabel(field, "Den Schlüsselbaum in Editor nicht anzeigen.");
    }

    private void supportFragments(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        supportFragments = new Button(field, SWT.CHECK);
        supportFragments.setSelection(PropertyPreferences.getInstance().isSupportFragments());
        createLabel(field, "Laden von Ressourcen aus Fragment-Projekten unterstützen");
    }

    private void loadOnlyFragmentResources(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        loadOnlyFragmentResources = new Button(field, SWT.CHECK);
        loadOnlyFragmentResources.setSelection(PropertyPreferences.getInstance().isLoadOnlyFragmentResources());
        createLabel(field, "Nur Ressourcen aus Fragment-Projekten laden (Host Plugin ignorieren)");
    }

    private void i18nEditorHeight(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        createLabel(field, "Editor-Höhe");
        i18nEditorHeight = new Text(field, SWT.BORDER);
        i18nEditorHeight.setText(String.valueOf(PropertyPreferences.getInstance().getI18nEditorHeight()));
        i18nEditorHeight.setTextLimit(3);
        setWidthInChars(i18nEditorHeight, 3);
        i18nEditorHeight.addKeyListener(new NumberTextKeyListener("Die 'Minimale Editor-Höhe' muss eine Zahl sein.", this));
    }

    private void fieldTabInsert(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        fieldTabInserts = new Button(field, SWT.CHECK);
        fieldTabInserts.setSelection(PropertyPreferences.getInstance().isFieldTabInsert());
        createLabel(field, "Die Tab-Taste f\u00FCgt einen Tab in die Lokalisierungstext ein.");
    }

    private void keyTreeExpanded(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        keyTreeExpanded = new Button(field, SWT.CHECK);
        keyTreeExpanded.setSelection(PropertyPreferences.getInstance().isEditorTreeExpanded());
        createLabel(field, "Schl\u00FCsselbaum standardm\u00E4\u00DFig expandiert \u00F6ffnen.");
    }

    private void keyTreeHierachical(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        keyTreeHierarchical = new Button(field, SWT.CHECK);
        keyTreeHierarchical.setSelection(PropertyPreferences.getInstance().isEditorTreeHierachical());
        createLabel(field, "Schl\u00FCsselbaum standardm\u00E4\u00DFig als Hierarchie \u00F6ffnen.");
    }

    private void supportNl(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        supportNL = new Button(field, SWT.CHECK);
        supportNL.setSelection(PropertyPreferences.getInstance().isSUpportNl());
        createLabel(field, "Unterst\u00FCtzen der Eclipse \"nl\"-Struktur f\u00FCr PlugIn-Internationalisierung.");
    }

    private void convertEncodedToUnicode(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        convertEncodedToUnicode = new Button(field, SWT.CHECK);
        convertEncodedToUnicode.setSelection(PropertyPreferences.getInstance().isConvertEncodedToUnicode());
        createLabel(field, "Umwandeln von \\uXXXX Werten in Unicode-Zeichen, wenn aus Properties-Datei gelesen wird.");
    }

    private void keyGroupSeparator(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        createLabel(field, "Schl\u00FCsselgruppenseparator:");
        keyGroupSeparator = new Text(field, SWT.BORDER);
        keyGroupSeparator.setText(PropertyPreferences.getInstance().getKeyGroupSeparator());
        keyGroupSeparator.setTextLimit(2);
    }

    @Override
    public boolean performOk() {
        PropertyPreferences.getInstance().setKeyGroupSeparator(keyGroupSeparator.getText());
        PropertyPreferences.getInstance().setI18nEditorHeight(Integer.valueOf(i18nEditorHeight.getText()));
        PropertyPreferences.getInstance().isFieldTabInsert(fieldTabInserts.getSelection());
        PropertyPreferences.getInstance().isEditorTreeExpanded(keyTreeExpanded.getSelection());
        PropertyPreferences.getInstance().isEditorTreeHidden(editorTreeHidden.getSelection());
        PropertyPreferences.getInstance().isEditorTreeHierachical(keyTreeHierarchical.getSelection());
        PropertyPreferences.getInstance().isSUpportNl(supportNL.getSelection());
        PropertyPreferences.getInstance().isSupportFragments(supportFragments.getSelection());
        PropertyPreferences.getInstance().isLoadOnlyFragmentResources(loadOnlyFragmentResources.getSelection());
        PropertyPreferences.getInstance().isConvertEncodedToUnicode(convertEncodedToUnicode.getSelection());
        return true;
    }
}
