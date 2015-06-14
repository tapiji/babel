package org.eclipse.e4.babel.editor.text.property;


import java.util.List;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;


final class PropertyCommentScanner extends APropertyScanner {

    private PropertyCommentScanner() {
        super();
        setDefaultReturnToken(getToken(PropertyColorManager.COMMENT));
    }

    private static class LazyHolder {

        private static final PropertyCommentScanner INSTANCE = new PropertyCommentScanner();
    }

    public static PropertyCommentScanner getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    void addRules(final List<IRule> rules) {
        final IToken tokenComment = getToken(PropertyColorManager.COMMENT);
        rules.add(new SingleLineRule("!", null, tokenComment));
        rules.add(new SingleLineRule("#", null, tokenComment));
    }
}
