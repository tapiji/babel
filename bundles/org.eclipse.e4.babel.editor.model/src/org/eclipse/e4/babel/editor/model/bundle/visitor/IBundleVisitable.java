package org.eclipse.e4.babel.editor.model.bundle.visitor;

/**
 * Makes a bundle-related resource visitable by a an
 * {@link IBundleVisitor}. 
 * 
 * @author Pascal Essiembre
 */
public interface IBundleVisitable {

    /**
     * Accepts the visitor by passing itself and/or appropriate resources to it.
     * 
     * @param visitor the object to visit
     * @param passAlongArgument optional argument passed to the visitor
     */
    void accept(IBundleVisitor visitor, Object passAlongArgument);
}

