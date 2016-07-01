package org.eclipse.e4.babel.editor.model.bundle.listener;

/**
 * Convenience implementation of {@link IBundleChangeListener} allowing to
 * override only required methods.
 * 
 * @author Christian Behon
 */
public abstract class BundleChangeAdapter implements IBundleChangeListener {

    /**
     * Constructor.
     */
    public BundleChangeAdapter() {
        super();
    }

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
