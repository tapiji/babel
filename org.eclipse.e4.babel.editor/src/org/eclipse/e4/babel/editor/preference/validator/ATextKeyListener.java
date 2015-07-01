package org.eclipse.e4.babel.editor.preference.validator;


import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.widgets.Text;


abstract class ATextKeyListener extends KeyAdapter {

    private final Map<Text, String> errors = new HashMap<Text, String>();
    private final PreferencePage preferencePage;
    private final String errorMessage;

    public ATextKeyListener(final PreferencePage preferencePage, final String errorMessage) {
        this.preferencePage = preferencePage;
        this.errorMessage = errorMessage;
    }

    protected void handleError(final boolean isOperationAllowed, final Text message) {
        if (isOperationAllowed) {
            errors.remove(message);
            if (errors.isEmpty()) {
                preferencePage.setErrorMessage(null);
                preferencePage.setValid(true);
            } else {
                preferencePage.setErrorMessage(errors.values().iterator().next());
            }
        } else {
            errors.put(message, errorMessage);
            preferencePage.setErrorMessage(errorMessage);
            preferencePage.setValid(false);
        }
    }
}
