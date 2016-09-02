package org.eclipse.e4.babel.core.api;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.internal.file.AbstractFileCreator;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;



public interface IResourceFactory {

    /**
     * Gets the editor display name.
     * @return editor display name
     */
    public abstract String getEditorDisplayName();

    /**
     * Get the {@link SourceEditor}s of this factory,
     * that reference all message/properties files
     * this factory knows of.
     * <p>
     * This method should only be called after 
     * {@link #init(IEditorSite, IFile)} was called.
     * </p>
     * @return All {@link SourceEditor}s of this factory.
     */
    public abstract List<SourceEditor> getSourceEditors();

    /**
     * Adds the given resource to the factory.
     * 
     * @param resource The resource to add.
     * @param locale The locale of the resource.
     */
    public abstract SourceEditor addResource(IPropertyResource fileDocument, Locale locale);

    /**
     * Gets a properties file creator.
     * @return properties file creator
     */
   public abstract AbstractFileCreator getPropertiesFileCreator();

    /**
     * Returns true if the resource factory is responsible for
     * the specified file resource.
     * 
     * @param file the file to check
     * @return if responsible
     * @throws CoreException
     */
    public abstract boolean isResponsible(IPropertyResource fileDocument) throws CoreException;

    /**
     * A factory should initialize its {@link SourceEditor}s
     * in this method.
     * 
     * @param site the editor site
     * @param file the file resource
     * @throws CoreException
     * @throws IOException 
     */
    public abstract void init(IPropertyResource fileDocument) throws CoreException, IOException;
    
    /**
     * TODO
     * @return
     */
    public abstract boolean isExternal();

}