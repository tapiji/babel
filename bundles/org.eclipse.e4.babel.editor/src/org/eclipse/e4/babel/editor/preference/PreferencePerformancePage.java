package org.eclipse.e4.babel.editor.preference;


import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.e4.babel.editor.preference.validator.DoubleTextKeyListener;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipselabs.e4.tapiji.logger.Log;


public final class PreferencePerformancePage extends APreferencePage {

    private static final String TAG = PreferencePerformancePage.class.getSimpleName();

    private final Button[] reportSimValsMode = new Button[2];

    private Button reportMissingVals;
    private Button reportDuplVals;
    private Button reportSimVals;
    private Text reportSimPrecision;

    public PreferencePerformancePage(IEventBroker eventBroker) {
        super("Performance",eventBroker);
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

        initValues();
        performRefresh();
        return composite;
    }

    private void reportMissingValues(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        reportMissingVals = new Button(field, SWT.CHECK);
        createLabel(field, "Zeige Schl\u00FCssel mit einem oder mehr fehlenden Werten.");
    }

    private void reportDuplicateValues(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        reportDuplVals = new Button(field, SWT.CHECK);
        createLabel(field, "Zeige Schl\u00FCssel, die sich gleiche Inhalte/Werte innerhalb einer Lokale teilen.");
    }

    private void reportSimilarValues(final Composite composite) {
        final Composite field = createFieldComposite(composite);
        reportSimVals = new Button(field, SWT.CHECK);
        reportSimVals.addListener(SWT.Selection, (e)->performRefresh());
        createLabel(field, "Zeige Schl\u00FCssel, die sich gleiche Inhalte/Werte innerhalb einer Lokale teilen.");

        final Composite simValModeGroup = new Composite(composite, SWT.NONE);
        final GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = indentPixels;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        simValModeGroup.setLayout(gridLayout);

        reportSimValsMode[0] = new Button(simValModeGroup, SWT.RADIO);
        createLabel(simValModeGroup, "Nutze Anzahl identischer Worte.");

        reportSimValsMode[1] = new Button(simValModeGroup, SWT.RADIO);
        createLabel(simValModeGroup, "Nutze Levensthein Distanz.");
    }

    private void reportPrecisionLevel(final Composite composite) {
        final Composite field = createFieldComposite(composite, indentPixels);
        createLabel(field, "Genauigkeitslevel (zw. 0 und 1):");
        reportSimPrecision = new Text(field, SWT.BORDER);
        reportSimPrecision.setTextLimit(6);
        setWidthInChars(reportSimPrecision, 6);
        reportSimPrecision.addKeyListener(new DoubleTextKeyListener("Der Genauigkeitslevel mu\u00DF zwischen 0 und 1 sein.", 0, 1, this));
    }

    private void performRefresh() {
        if (null != reportSimVals) {
            final boolean isReportingSimilar = reportSimVals.getSelection();
            for (final Button element : reportSimValsMode) {
                element.setEnabled(isReportingSimilar);
            }
            reportSimPrecision.setEnabled(isReportingSimilar);
        }
    }

    private void initValues() {
        reportSimPrecision.setText(String.valueOf(PropertyPreferences.getInstance().getReportSimilarValuesPrecision()));
        reportSimValsMode[1].setSelection(PropertyPreferences.getInstance().isReportSimilarValuesLevensthein());
        reportSimValsMode[0].setSelection(PropertyPreferences.getInstance().isReportSimilarValuesWordCompare());
        reportSimVals.setSelection(PropertyPreferences.getInstance().isReportSimilarValues());
        reportDuplVals.setSelection(PropertyPreferences.getInstance().isReportDuplicateValues());
        reportMissingVals.setSelection(PropertyPreferences.getInstance().isReportMissingValues());
        reportSimVals.setSelection(PropertyPreferences.getInstance().isReportSimilarValues());
    }

    @Override
    public boolean performOk() {
        Log.d(TAG, "performOk");
        if (null != reportMissingVals) {
            PropertyPreferences.getInstance().isReportMissingValues(reportMissingVals.getSelection());
        }
        if (null != reportDuplVals) {
            PropertyPreferences.getInstance().isReportDuplicateValues(reportDuplVals.getSelection());
        }
        if (null != reportSimVals) {
            PropertyPreferences.getInstance().isReportSimilarValues(reportSimVals.getSelection());
        }
        if (null != reportMissingVals) {
            PropertyPreferences.getInstance().isReportSimilarValuesWordCompare(reportSimValsMode[0].getSelection());
            PropertyPreferences.getInstance().isReportSimliarValuesLevensthein(reportSimValsMode[1].getSelection());
        }
        if (null != reportSimPrecision) {
            PropertyPreferences.getInstance().setReportSimilarValuesPrecision(Double.parseDouble(reportSimPrecision.getText()));
        }
        performRefresh();
        return true;
    }

    @Override
    protected void performDefaults() {
        super.performDefaults();
        Log.d(TAG, "performDefaults");
        PropertyPreferences.getInstance().resetPerformanceProperties();
        initValues();
        performRefresh();
    }
}
