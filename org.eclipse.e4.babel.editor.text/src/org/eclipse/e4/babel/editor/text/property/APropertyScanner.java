package org.eclipse.e4.babel.editor.text.property;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;


abstract class APropertyScanner extends BufferedRuleBasedScanner {

    private static List<IRule> rules;

    public APropertyScanner() {
        super();
        rules = new ArrayList<IRule>();
        addRules(rules);
        commitRules();
    }

    abstract void addRules(List<IRule> rules);

    private void commitRules() {
        setRules(rules.toArray(new IRule[rules.size()]));
    }

    protected Token getToken(final RGB rgb) {
        return new Token(new TextAttribute(getColor(rgb)));
    }

    private Color getColor(final RGB rgb) {
        return PropertyColorManager.getInstance().getColor(rgb);
    }
}
