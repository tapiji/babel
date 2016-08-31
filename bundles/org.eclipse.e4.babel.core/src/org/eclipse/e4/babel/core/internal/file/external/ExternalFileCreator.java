package org.eclipse.e4.babel.core.internal.file.external;

import java.util.Locale;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.babel.core.internal.file.AbstractFileCreator;

public class ExternalFileCreator extends AbstractFileCreator {

    private final String dir;
    private final String baseFileName;
    private final String extension;

    /**
     * Constructor
     * 
     * @param dir directory in which to create the file
     * @param baseFileName base name of file to create
     * @param extension file extension
     */
    public ExternalFileCreator(final String dir, final String baseFileName, final String extension) {
        super();
        this.dir = dir;
        this.baseFileName = baseFileName;
        this.extension = extension;
    }
    
    @Override
    protected IPath buildFilePath(Locale locale) throws CoreException {
        IPath path = new Path(dir);
        path = path.append(baseFileName);
        if (locale != null) {
            path = new Path(path.toString() + "_" + locale.toString());
        }
        return path.addFileExtension(extension);
    }

}
