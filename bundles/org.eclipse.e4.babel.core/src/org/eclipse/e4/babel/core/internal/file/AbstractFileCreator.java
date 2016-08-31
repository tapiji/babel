package org.eclipse.e4.babel.core.internal.file;


import java.io.IOException;
import java.util.Locale;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.e4.babel.core.internal.generator.PropertiesGenerator;
import org.eclipse.e4.babel.core.preference.PropertyPreferences;
import org.eclipse.jdt.annotation.Nullable;


public abstract class AbstractFileCreator {


    /**
     * Creates a propertiles file.
     * 
     * @param locale locale representing properties file
     * @return the properties file
     * @return Null when the file already exists
     * @throws CoreException when a problem occurs
     **/
    @Nullable
    public IFile createIFile(final Locale locale) throws IOException, CoreException {
        return null;
    }

    public IFile createFile(final Locale locale) throws CoreException {
        IPath filePath = buildFilePath(locale);
        IFile file = null;

       
        //Files.write(file.getRawLocation().toFile().toPath(), content.getBytes());
        return file;
    }

    private String generatedBy() {
        String content = "";
        if (PropertyPreferences.getInstance().isGeneratedByEnabled()) {
            content = PropertiesGenerator.GENERATED_BY;
        }
        return content;
    }

    protected abstract IPath buildFilePath(Locale locale) throws CoreException;
}
