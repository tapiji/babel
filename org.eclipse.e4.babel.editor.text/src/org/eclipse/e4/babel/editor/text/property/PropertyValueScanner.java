package org.eclipse.e4.babel.editor.text.property;


import java.util.List;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;


final class PropertyValueScanner extends APropertyScanner {

    private PropertyValueScanner() {
        setDefaultReturnToken(getToken(PropertyColorManager.STRING));
    }

    @Override
    void addRules(final List<IRule> rules) {
        final IToken tokenValue = getToken(PropertyColorManager.STRING);
        rules.add(new SingleLineRule("=", null, tokenValue, '\\', true, true));
        rules.add(new SingleLineRule(" ", null, tokenValue, '\\', true, true));
        rules.add(new SingleLineRule("\t", null, tokenValue, '\\', true, true));

        rules.add(new WhitespaceRule(new PropertyWhiteSpaceDetector()));
    }

    private static class LazyHolder {

        private static final PropertyValueScanner INSTANCE = new PropertyValueScanner();
    }

    public static PropertyValueScanner getInstance() {
        return LazyHolder.INSTANCE;
    }
}
