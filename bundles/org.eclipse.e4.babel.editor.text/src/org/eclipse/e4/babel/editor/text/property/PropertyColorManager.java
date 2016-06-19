package org.eclipse.e4.babel.editor.text.property;


import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;


final class PropertyColorManager {

    public static final RGB COMMENT = new RGB(63, 127, 95);
    public static final RGB STRING = new RGB(48, 0, 253);
    public static final RGB DEFAULT = new RGB(0, 0, 0);

    private static final int COLOR_MAP_CAPACITY = 3;

    private static final Map<RGB, Color> COLORS = new HashMap<RGB, Color>(COLOR_MAP_CAPACITY);

    static {
        COLORS.put(COMMENT, new Color(Display.getCurrent(), COMMENT));
        COLORS.put(DEFAULT, new Color(Display.getCurrent(), DEFAULT));
        COLORS.put(STRING, new Color(Display.getCurrent(), STRING));
    }

    private PropertyColorManager() {
        super();
    }

    public Color getColor(final RGB rgb) {
        Color color = COLORS.get(rgb);
        if (null == color) {
            color = new Color(Display.getCurrent(), COMMENT);
            COLORS.put(rgb, color);
        }
        return color;
    }

    private static class LazyHolder {

        private static final PropertyColorManager INSTANCE = new PropertyColorManager();
    }

    public static PropertyColorManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void dispose() {
        for (final Color color : COLORS.values()) {
            color.dispose();
        }
    }
}
