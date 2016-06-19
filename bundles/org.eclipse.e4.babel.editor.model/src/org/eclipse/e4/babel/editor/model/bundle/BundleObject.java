package org.eclipse.e4.babel.editor.model.bundle;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleChangeListener;
import org.eclipse.e4.babel.editor.model.bundle.listener.BundleEvent;
import org.eclipselabs.e4.tapiji.logger.Log;


public abstract class BundleObject {

  private static final String TAG = BundleObject.class.getSimpleName();
  private final List<BundleChangeListener> listeners = new ArrayList<>();

  protected <T> void fireAdd(final T object) {
    listeners.stream()
             .forEach(listener -> listener.add(BundleEvent.create(object)));
    Log.d(TAG, "fireAdd: " + object.getClass()
                                   .getSimpleName() + " listener cnt: " + listeners.size());
  }

  protected <T> void fireRemove(final T object) {
    Log.d(TAG, "fireRemove: " + object.getClass()
                                      .getSimpleName());
    listeners.stream()
             .forEach(listener -> listener.remove(BundleEvent.create(object)));
  }

  protected <T> void fireModify(final T object) {
    Log.d(TAG, "fireModify: " + object.getClass()
                                      .getSimpleName() + " listener cnt: " + listeners.size());
    listeners.stream()
             .forEach(listener -> listener.modify(BundleEvent.create(object)));
  }

  public void addChangeLIstener(final BundleChangeListener listener) {
    Log.d(TAG, "addChangeLIstener: " + listener.getClass()
                                               .getSimpleName() + " listener cnt: " + listeners.size());
    listeners.add(0, listener);
  }

  public void removeChangeListener(final BundleChangeListener listener) {
    Log.d(TAG, "removeChangeListener: " + listener.getClass()
                                                  .getSimpleName() + " listener cnt: " + listeners.size());
    listeners.remove(listener);
  }

  public void removeAllListeners() {
    listeners.clear();
  }
}
