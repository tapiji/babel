package test.org.eclipse.e4.babel.editor.text.test;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.babel.logger.Log;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;


public class FileSourceTest {

    private static final String TAG = FileSourceTest.class.getSimpleName();

    @BeforeClass
    public static void setup() {
    }

    @Test
    public void readFileTest() throws CoreException {

        //   IFile file = null;
        //  try {
        //  file = FileUtils.getResourceBundleRef("source/bundle.properties", "linked");
        // } catch (CoreException e) {
        // TODO Auto-generated catch block
        //  e.printStackTrace();
        //}
        IWorkspace ws = ResourcesPlugin.getWorkspace();
        IProject project = ws.getRoot().getProject("External Files");
        if (!project.exists()) project.create(null);
        if (!project.isOpen()) project.open(null);

        Bundle bundle = Platform.getBundle("org.eclipse.e4.babel.editor.text.test");
        URL fileURL = bundle.getEntry("source/bundle.properties");

        File file = null;
        try {
            file = new File(FileLocator.resolve(fileURL).toURI());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        IPath location = new Path(file.getAbsolutePath());
        IFile iFile = project.getFile(location.lastSegment());
        iFile.createLink(location, IResource.NONE, null);

        //  IFileDocument res = FileResource.create(file);
        Log.d(TAG, "" + iFile.getName());
        Log.d(TAG, "" + iFile.getRawLocation());

    }
}
