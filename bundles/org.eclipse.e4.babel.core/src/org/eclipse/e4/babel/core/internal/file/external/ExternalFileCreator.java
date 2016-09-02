package org.eclipse.e4.babel.core.internal.file.external;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.internal.file.AbstractFileCreator;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyFileResource;
import org.eclipse.e4.babel.logger.Log;


/**
 * Creates a standard properties file.
 * 
 * @author Christian Behon
 */
public final class ExternalFileCreator extends AbstractFileCreator {

    private static final String TAG = ExternalFileCreator.class.getSimpleName();
    private final String dir;
    private final String baseFileName;
    private final String extension;

    /**
     * Constructor
     * 
     * @param dir directory in which to create the file
     * @param baseFileName base name of file to create
     * @param extension extension of the file
     */
    public ExternalFileCreator(final String dir, final String baseFileName, final String extension) {
        super();
        this.dir = dir;
        this.baseFileName = baseFileName;
        this.extension = extension;
        Log.d(TAG, "Directory: " + dir + " BaseFilename: " + baseFileName + " Extension: " + extension);
    }


    /*
     * (non-Javadoc)
     * @see org.eclipse.e4.babel.core.internal.file.AbstractFileCreator#createPropertyResource(java.util.Locale,
     * java.lang.String)
     */
    @Override
    protected IPropertyResource createPropertyResource(Locale locale, String content) throws CoreException, IOException {

        Path path = null;
        if (locale != null) {
            path = Paths.get(dir, baseFileName + '_' + locale.toString() + '.' + extension);
        } else {
            path = Paths.get(dir, baseFileName + '.' + extension);
        }

        final File file = path.toFile();
        if (file.exists()) {
            throw new IOException("File already exists: " + file.getName());
        }
        Log.d(TAG, "FILE: " + path.toFile().toString());
        return PropertyFileResource.create(file, content);
    }
}
