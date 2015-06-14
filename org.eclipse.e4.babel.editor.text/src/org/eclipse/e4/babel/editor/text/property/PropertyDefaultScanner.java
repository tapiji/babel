package org.eclipse.e4.babel.editor.text.property;


import java.util.List;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.WhitespaceRule;


final class PropertyDefaultScanner extends APropertyScanner {

    private PropertyDefaultScanner() {
        setDefaultReturnToken(getToken(PropertyColorManager.DEFAULT));
    }

    @Override
    void addRules(final List<IRule> rules) {
        rules.add(new WhitespaceRule(new PropertyWhiteSpaceDetector()));
    }

    private static class LazyHolder {

        private static final PropertyDefaultScanner INSTANCE = new PropertyDefaultScanner();
    }

    public static PropertyDefaultScanner getInstance() {
        return LazyHolder.INSTANCE;
    }
}
