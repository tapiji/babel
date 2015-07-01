package org.eclipse.e4.babel.editor.preference;


import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public final class PreferenceReportingPage extends APreferencePage {

    private Combo reportMissingVals;
    private Combo reportDuplVals;
    private Combo reportSimVals;
    private Text reportSimPrecision;
    private Button[] reportSimValsMode = new Button[2];

    public PreferenceReportingPage() {
        super("Performance");
    }

    @Override
    protected Control createContents(Composite parent) {
        IPreferenceStore prefs = getPreferenceStore();
        Composite field = null;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        new Label(composite, SWT.NONE).setText("Die nachfolgenden Funktionen k\u00F6nnen signifikanten Einflu\u00DF auf die Arbeitsgeschwindigkeit haben.");
        new Label(composite, SWT.NONE).setText("Insbesondere bei gro\u00DFen Dateien. Mit Bedacht verwenden.");
        new Label(composite, SWT.NONE).setText(" ");

        // Report missing values?
        field = createFieldComposite(composite);
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        field.setLayoutData(gridData);
        new Label(field, SWT.NONE).setText("Zeige Schl\u00FCssel mit einem oder mehr fehlenden Werten.");
        reportMissingVals = new Combo(field, SWT.READ_ONLY);
        // populateCombo(reportMissingVals, prefs.getInt(MsgEditorPreferences.REPORT_MISSING_VALUES_LEVEL));
        // reportMissingVals.setSelection(
        // prefs.getBoolean(MsgEditorPreferences.REPORT_MISSING_VALUES));

        // Report duplicate values?
        field = createFieldComposite(composite);
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        field.setLayoutData(gridData);
        new Label(field, SWT.NONE).setText("Zeige Schl\u00FCssel, die sich gleiche Inhalte/Werte innerhalb einer Lokale teilen.");
        reportDuplVals = new Combo(field, SWT.READ_ONLY);
        // populateCombo(reportDuplVals, prefs.getInt(MsgEditorPreferences.REPORT_DUPL_VALUES_LEVEL));

        // Report similar values?
        field = createFieldComposite(composite);
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        field.setLayoutData(gridData);

        new Label(field, SWT.NONE).setText("Zeige Schl\u00FCssel, die sich gleiche Inhalte/Werte innerhalb einer Lokale teilen.");
        reportSimVals = new Combo(field, SWT.READ_ONLY);
        //populateCombo(reportSimVals, prefs.getInt(MsgEditorPreferences.REPORT_SIM_VALUES_LEVEL));
        reportSimVals.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent event) {
                //  refreshEnabledStatuses();
            }
        });

        Composite simValModeGroup = new Composite(composite, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = indentPixels;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        simValModeGroup.setLayout(gridLayout);

        // Report similar values: word count
        reportSimValsMode[0] = new Button(simValModeGroup, SWT.RADIO);
        //reportSimValsMode[0].setSelection(prefs.getBoolean(MsgEditorPreferences.REPORT_SIM_VALUES_WORD_COMPARE));
        new Label(simValModeGroup, SWT.NONE).setText("Nutze Anzahl identischer Worte.");

        // Report similar values: Levensthein
        reportSimValsMode[1] = new Button(simValModeGroup, SWT.RADIO);
        //  reportSimValsMode[1].setSelection(prefs.getBoolean(MsgEditorPreferences.REPORT_SIM_VALUES_LEVENSTHEIN));
        new Label(simValModeGroup, SWT.NONE).setText("Nutze Levensthein Distanz.");

        // Report similar values: precision level
        field = createFieldComposite(composite, indentPixels);
        new Label(field, SWT.NONE).setText("Genauigkeitslevel (zw. 0 und 1):");
        reportSimPrecision = new Text(field, SWT.BORDER);
        //reportSimPrecision.setText(prefs.getString(MsgEditorPreferences.REPORT_SIM_VALUES_PRECISION));
        reportSimPrecision.setTextLimit(6);
        setWidthInChars(reportSimPrecision, 6);
        reportSimPrecision.addKeyListener(new DoubleTextValidatorKeyListener("Der Genauigkeitslevel mu\u00DF zwischen 0 und 1 sein.", 0, 1));


        return composite;
    }
}
