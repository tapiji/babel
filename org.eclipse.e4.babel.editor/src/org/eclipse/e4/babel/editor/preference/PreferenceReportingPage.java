package org.eclipse.e4.babel.editor.preference;


import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.preference.validator.DoubleTextKeyListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;


public final class PreferenceReportingPage extends APreferencePage {

    private Button reportMissingVals;
    private Button reportDuplVals;
    private Button reportSimVals;
    private Text reportSimPrecision;
    private final Button[] reportSimValsMode = new Button[2];

    public PreferenceReportingPage() {
        super("Performance");
    }

    @Override
    protected Control createContents(final Composite parent) {
        final Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        createLabel(composite, "Die nachfolgenden Funktionen k\u00F6nnen signifikanten Einflu\u00DF auf die Arbeitsgeschwindigkeit haben.");
        createLabel(composite, "Insbesondere bei gro\u00DFen Dateien. Mit Bedacht verwenden.");
        createLabel(composite, " ");

        reportMissingValues(composite);
        reportDuplicateValues(composite);
        reportSimilarValues(composite);
        reportPrecisionLevel(composite);
        performRefresh();
        return composite;
    }

    private void reportMissingValues(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        reportMissingVals = new Button(field, SWT.CHECK);
        reportMissingVals.setSelection(PropertyPreferences.getInstance().isReportMissingValues());
        createLabel(field, "Zeige Schl\u00FCssel mit einem oder mehr fehlenden Werten.");
    }

    private void reportDuplicateValues(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        reportDuplVals = new Button(field, SWT.CHECK);
        reportDuplVals.setSelection(PropertyPreferences.getInstance().isReportDuplicateValues());
        createLabel(field, "Zeige Schl\u00FCssel, die sich gleiche Inhalte/Werte innerhalb einer Lokale teilen.");
    }

    private void reportSimilarValues(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        reportSimVals = new Button(field, SWT.CHECK);
        reportSimVals.setSelection(PropertyPreferences.getInstance().isReportSimilarValues());
        reportSimVals.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent event) {
                performRefresh();
            }
        });
        createLabel(field, "Zeige Schl\u00FCssel, die sich gleiche Inhalte/Werte innerhalb einer Lokale teilen.");

        final Composite simValModeGroup = new Composite(composite, SWT.NONE);
        final GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = indentPixels;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        simValModeGroup.setLayout(gridLayout);

        // Report similar values: word count
        reportSimValsMode[0] = new Button(simValModeGroup, SWT.RADIO);
        reportSimValsMode[0].setSelection(PropertyPreferences.getInstance().isReportSimilarValuesWordCompare());
        createLabel(simValModeGroup, "Nutze Anzahl identischer Worte.");

        // Report similar values: Levensthein
        reportSimValsMode[1] = new Button(simValModeGroup, SWT.RADIO);
        reportSimValsMode[1].setSelection(PropertyPreferences.getInstance().isReportSimliarValuesLevensthein());
        createLabel(simValModeGroup, "Nutze Levensthein Distanz.");
    }

    private void reportPrecisionLevel(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        createLabel(field, "Genauigkeitslevel (zw. 0 und 1):");
        reportSimPrecision = new Text(field, SWT.BORDER);
        reportSimPrecision.setText(String.valueOf(PropertyPreferences.getInstance().getReportSimilarValuesPrecision()));
        reportSimPrecision.setTextLimit(6);
        setWidthInChars(reportSimPrecision, 6);
        reportSimPrecision.addKeyListener(new DoubleTextKeyListener("Der Genauigkeitslevel mu\u00DF zwischen 0 und 1 sein.", 0, 1, this));
    }

    @Override
    public boolean performOk() {
        PropertyPreferences.getInstance().isReportMissingValues(reportMissingVals.getSelection());
        PropertyPreferences.getInstance().isReportDuplicateValues(reportDuplVals.getSelection());
        PropertyPreferences.getInstance().isReportSimilarValues(reportSimVals.getSelection());
        PropertyPreferences.getInstance().isReportSimilarValuesWordCompare(reportSimValsMode[0].getSelection());
        PropertyPreferences.getInstance().isReportSimliarValuesLevensthein(reportSimValsMode[1].getSelection());
        PropertyPreferences.getInstance().setReportSimilarValuesPrecision(Double.parseDouble(reportSimPrecision.getText()));
        performRefresh();
        return true;
    }

    private void performRefresh() {
        final boolean isReportingSimilar = reportSimVals.getSelection();
        for (final Button element : reportSimValsMode) {
            element.setEnabled(isReportingSimilar);
        }
        reportSimPrecision.setEnabled(isReportingSimilar);
    }

}
