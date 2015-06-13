package org.eclipse.e4.babel.editor.text.prop;


import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;


final class ColorManager {

    private static final RGB COMMENT = new RGB(128, 0, 0);
    private static final RGB STRING = new RGB(0, 128, 0);
    private static final RGB DEFAULT = new RGB(0, 0, 0);
    private static final RGB EQUALS = new RGB(0, 0, 128);

    private static final int COLOR_MAP_CAPACITY = 15;

    private static final Map<RGB, Color> COLORS = new HashMap<RGB, Color>(COLOR_MAP_CAPACITY);

    static {
        COLORS.put(COMMENT, new Color(Display.getCurrent(), COMMENT));
        COLORS.put(DEFAULT, new Color(Display.getCurrent(), DEFAULT));
        COLORS.put(STRING, new Color(Display.getCurrent(), STRING));
        COLORS.put(EQUALS, new Color(Display.getCurrent(), EQUALS));
    }

    private ColorManager() {
        // Hid constructor
    }

    public Color getColor(final RGB rgb) {
        Color color = COLORS.get(rgb);
        if (null == color) {
            color = new Color(Display.getCurrent(), COMMENT);
            COLORS.put(rgb, color);
        }
        return color;
    }

    public void dispose() {
        for (Color color : COLORS.values()) {
            color.dispose();
        }
    }

    public static ColorManager create() {
        return new ColorManager();
    }
}
