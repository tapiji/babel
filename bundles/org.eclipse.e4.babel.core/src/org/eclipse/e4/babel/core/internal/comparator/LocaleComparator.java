package org.eclipse.e4.babel.core.internal.comparator;


import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;


public final class LocaleComparator implements Comparator<Locale> {

    private LocaleComparator() {
        super();
    }

    @Override
    public int compare(Locale o1, Locale o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 != null && o2 == null) {
            return 1;
        }
        if (o1 == null && o2 != null) {
            return -1;
        }
        final Collator c = Collator.getInstance();
        c.setStrength(Collator.PRIMARY);
        return c.compare(o1.getDisplayName(), o2.getDisplayName());
    }

    public static Comparator<? super Locale> create() {
        return new LocaleComparator();
    }

}
