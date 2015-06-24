import static org.junit.Assert.assertEquals;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.editor.text.PropertiesTextEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.eclipselabs.e4.tapiji.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;


public class SourceViewerTest {

    private static final String TAG = SourceViewerTest.class.getSimpleName();

    @BeforeClass
    public static void setup() {

    }

    @Test
    public void propertiesTextEditorFileTest() {
        final Display display = Display.getCurrent();
        final Shell shell = new Shell(display, SWT.NONE);
        final Composite composite = new Composite(shell, SWT.NONE);
        IFile file;
        PropertiesTextEditor propertiesTextEditor = null;
        try {
            file = FileUtils.getResourceBundleRef("/media/nucle/xubuntu/github-tapiji/babel/org.eclipse.e4.babel.editor.text.test/property/bundle.properties", "sds");
            propertiesTextEditor = new PropertiesTextEditor(composite, file);
        } catch (final CoreException e) {
            e.printStackTrace();
        }
        if (null != propertiesTextEditor) {
            Log.d(TAG, "CONTENT" + propertiesTextEditor.getSourceViewer().getDocument().get());
            Log.d(TAG, "" + propertiesTextEditor.getSourceViewer().getDocument().getNumberOfLines());
        }
        assertEquals(propertiesTextEditor.getSourceViewer().getDocument().getNumberOfLines(), 38);
    }
}
