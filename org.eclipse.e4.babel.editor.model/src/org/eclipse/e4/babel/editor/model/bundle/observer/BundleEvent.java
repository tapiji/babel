package org.eclipse.e4.babel.editor.model.bundle.observer;


public final class BundleEvent<T> {

    private final T data;

    private BundleEvent(final T data) {
        this.data = data;
    }

    public T data() {
        return this.data;
    }

    public static <T> BundleEvent<T> create(T object) {
        return new BundleEvent<T>(object);
    }
}
