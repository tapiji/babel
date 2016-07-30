package org.eclipse.e4.babel.core.utils;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public final class UIUtils {
    
    private UIUtils() {
        // Only static access allowed
    }
    
    /**
     * Gets a system color.
     * 
     * @param colorId
     *            SWT constant
     * @return system color
     */
    public static Color getSystemColor(int colorId) {
        return Display.getCurrent().getSystemColor(colorId);
    }
    
    /**
     * Creates a font by altering the font associated with the given control and
     * applying the provided style (size is unaffected).
     * 
     * @param control
     *            control we base our font data on
     * @param style
     *            style to apply to the new font
     * @return newly created font
     */
    public static Font createFont(Control control, int style) {
        // TODO consider dropping in favor of control-less version?
        return createFont(control, style, 0);
    }

    /**
     * Creates a font by altering the font associated with the given control and
     * applying the provided style and relative size.
     * 
     * @param control
     *            control we base our font data on
     * @param style
     *            style to apply to the new font
     * @param relSize
     *            size to add or remove from the control size
     * @return newly created font
     */
    public static Font createFont(Control control, int style, int relSize) {
        // TODO consider dropping in favor of control-less version?
        FontData[] fontData = control.getFont().getFontData();
        for (int i = 0; i < fontData.length; i++) {
            fontData[i].setHeight(fontData[i].getHeight() + relSize);
            fontData[i].setStyle(style);
        }
        return new Font(control.getDisplay(), fontData);
    }
    
    /**
     * Gets a locale, null-safe, display name.
     * @param locale locale to get display name
     * @return display name
     */
    public static String getDisplayName(Locale locale) {
        if (locale == null) {
            return "Default";
        }
        return locale.getDisplayName();
    }

    /**
     * Creates a font by altering the system font and applying the provided
     * style and relative size.
     * 
     * @param style
     *            style to apply to the new font
     * @return newly created font
     */
    public static Font createFont(int style) {
        return createFont(style, 0);
    }

    /**
     * Creates a font by altering the system font and applying the provided
     * style and relative size.
     * 
     * @param style
     *            style to apply to the new font
     * @param relSize
     *            size to add or remove from the control size
     * @return newly created font
     */
    public static Font createFont(int style, int relSize) {
        Display display = Display.getCurrent();
        FontData[] fontData = display.getSystemFont().getFontData();
        for (int i = 0; i < fontData.length; i++) {
            fontData[i].setHeight(fontData[i].getHeight() + relSize);
            fontData[i].setStyle(style);
        }
        return new Font(display, fontData);
    }
    
    /**
     * Sorts given list of locales based on the {@link Locale#getDisplayName()} using 
     * {@link Collator} in the current locale.
     * null argument will be always first
     * 
     * @param locales to be sorted
     * @author LZaruba lukas.zaruba@gmail.com
     * @return sorted locales
     */
    public static List<Locale> sortLocales(Collection<Locale> locales) {
        ArrayList<Locale> result = new ArrayList<>(locales);
        result.sort(new Comparator<Locale>() {

            @Override
            public int compare(Locale o1, Locale o2) {
                if (o1 == null && o2 == null) return 0;
                if (o1 != null && o2 == null) return 1;
                if (o1 == null && o2 != null) return -1;
                Collator c = Collator.getInstance();
                c.setStrength(Collator.PRIMARY);
                return c.compare(o1.getDisplayName(), o2.getDisplayName());
            }
            
        });
        return result;
    }
    
    public static int getWidthInChars(final Control control, final int numOfChars) {
        final GC gc = new GC(control);
        final Point pointExtent = gc.textExtent("W");//$NON-NLS-1$
        gc.dispose();
        return numOfChars * pointExtent.x;
    }
    
}
