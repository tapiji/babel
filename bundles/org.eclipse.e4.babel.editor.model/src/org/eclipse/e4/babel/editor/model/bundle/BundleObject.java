package org.eclipse.e4.babel.editor.model.bundle;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipse.e4.babel.editor.model.bundle.listener.IBundleChangeListener;
import org.eclipse.e4.babel.logger.Log;


public abstract class BundleObject {

    private static final String TAG = BundleObject.class.getSimpleName();
    private final List<IBundleChangeListener> listeners = new ArrayList<>();

    protected <T> void fireAdd(final T object) {
        listeners.stream().forEach(listener -> listener.add(BundleEvent.create(object)));
        Log.d(TAG, "fireAdd: " + object+ " listener cnt: " + listeners.size());
    }

    protected <T> void fireRemove(final T object) {
        Log.d(TAG, "fireRemove: " + object);
        listeners.stream().forEach(listener -> listener.remove(BundleEvent.create(object)));
    }

    protected <T> void fireModify(final T object) {
        Log.d(TAG, "fireModify: " + object+ " listener cnt: " + listeners.size());
        listeners.stream().forEach(listener -> listener.modify(BundleEvent.create(object)));
    }

    protected <T> void fireSelect(final T object) {
        Log.d(TAG, "fireModify: " + object + " listener cnt: " + listeners.size());
        listeners.stream().forEach(listener -> listener.select(BundleEvent.create(object)));
    }

    public void addChangeListener(final IBundleChangeListener listener) {
        Log.d(TAG, "addChangeLIstener: " + listener.getClass().getSimpleName() + " listener cnt: " + listeners.size());
        listeners.add(0, listener);
    }

    public void removeChangeListener(final IBundleChangeListener listener) {
        Log.d(TAG, "removeChangeListener: " + listener.getClass().getSimpleName() + " listener cnt: " + listeners.size());
        listeners.remove(listener);
    }

    public void removeAllListeners() {
        listeners.clear();
    }
}
