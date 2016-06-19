package org.eclipse.e4.babel.editor.text.property;


import org.eclipse.jface.text.rules.IWhitespaceDetector;


final class PropertyWhiteSpaceDetector implements IWhitespaceDetector {

    @Override
    public boolean isWhitespace(final char character) {
        return ((character == ' ') || (character == '\t') || (character == '\n') || (character == '\r'));
    }

}
