package org.eclipse.e4.babel.editor.preference;


import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.e4.core.di.annotations.Creatable;


@Creatable
@Singleton
public class ResourceBundlePreferences {

    private static final String STORE_SORT_ORDER = "STORE/SORT_ORDER";

    private static final String TAG = ResourceBundlePreferences.class.getSimpleName();

    private Map<String, String> persistedState;

    @Inject
    public ResourceBundlePreferences() {
        // persistedState = part.getPersistedState();
    }
}
