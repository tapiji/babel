package org.eclipse.e4.babel.editor.model.bundle.listener;


public abstract class BundleChangeAdapter implements IBundleChangeListener {

  @Override
  public <T> void add(BundleEvent<T> event) {
    // noop 
  }

  @Override
  public <T> void remove(BundleEvent<T> event) {
    // noop
  }

  @Override
  public <T> void modify(BundleEvent<T> event) {
    // noop
  }

  @Override
  public <T> void select(BundleEvent<T> event) {
    // noop
  }
}
