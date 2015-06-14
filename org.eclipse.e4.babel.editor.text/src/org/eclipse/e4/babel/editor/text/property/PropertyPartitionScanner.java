package org.eclipse.e4.babel.editor.text.property;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;


public final class PropertyPartitionScanner extends RuleBasedPartitionScanner {

    public final static String PROPERTY_COMMENT = "__prop_comment";
    public final static String PROPERTY_VALUE = "__prop_value";

    public static String[] PARTITIONS = new String[] {PROPERTY_COMMENT, PROPERTY_VALUE};

    public PropertyPartitionScanner() {
        super();
        final IToken tokenValue = new Token(PROPERTY_VALUE);
        final IToken tokenComment = new Token(PROPERTY_COMMENT);

        final List<IPredicateRule> rules = new ArrayList<IPredicateRule>();

        rules.add(new EndOfLineRule("#", tokenComment, (char) 0, true));
        rules.add(new EndOfLineRule("!", tokenComment, (char) 0, true));

        rules.add(new SingleLineRule("=", null, tokenValue, '\\', true, true));
        rules.add(new SingleLineRule(" ", null, tokenValue, '\\', true, true));
        rules.add(new SingleLineRule("\t", null, tokenValue, '\\', true, true));

        setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
    }
}
