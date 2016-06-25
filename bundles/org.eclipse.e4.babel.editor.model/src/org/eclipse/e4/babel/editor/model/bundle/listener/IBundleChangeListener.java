package org.eclipse.e4.babel.editor.model.bundle.listener;


public interface IBundleChangeListener {

  public <T> void add(final BundleEvent<T> event);

  public <T> void remove(final BundleEvent<T> event);

  public <T> void modify(final BundleEvent<T> event);

  public <T> void select(final BundleEvent<T> event);
}
