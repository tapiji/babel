package org.eclipse.e4.babel.editor.text.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.babel.logger.Log;
import org.junit.BeforeClass;
import org.junit.Test;

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
        if (!project.exists())
            project.create(null);
        if (!project.isOpen())
            project.open(null);

        IPath location = new Path("/source/bundle.properties");
        IFile file = project.getFile(location.lastSegment());
        file.createLink(location, IResource.NONE, null);
      
      //  IFileDocument res = FileResource.create(file);
        Log.d(TAG,""+ file.getName());
        Log.d(TAG, ""+file.getRawLocation());
        

    }
}
