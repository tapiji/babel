package org.eclipse.e4.babel.editor.text.test;


import org.eclipse.swt.widgets.Display;
import org.junit.BeforeClass;
import org.junit.Test;


public class SourceViewerTest {

    private static final String TAG = SourceViewerTest.class.getSimpleName();

    private final Display display = Display.getCurrent();
    private final String lineSeparator = System.getProperty("line.separator");

    @BeforeClass
    public static void setup() {
    }

    @Test
    public void readPropertyFileTest() {
        /*final PropertiesTextEditor propertyFile = readPropertyFile(FileUtils.getRelativePath(SourceViewerTest.class).getPath() + "/property/read_bundle.properties");
        assertEquals(propertyFile.getSourceViewerDocument().getDocument().get().isEmpty(), false);*/
    }

    @Test
    public void savePropertyFileTest() {
       /* final PropertiesTextEditor propertyFile = readPropertyFile(FileUtils.getRelativePath(SourceViewerTest.class).getPath() + "/property/save_bundle.properties");
        assertEquals(propertyFile.getSourceViewer().getDocument().getNumberOfLines(), 1);
        final IDocument document = propertyFile.getSourceViewerDocument().getDocument();
        String content = document.get();
        content += createPropertyEntry("test=123");
        content += createPropertyEntry("line.two=hellow world");
        content += "line.three=hello world";
        propertyFile.getSourceViewerDocument().getDocument().set(content);
        propertyFile.getSourceViewerDocument().saveDocument();

        final PropertiesTextEditor propertyFileTwo = readPropertyFile(FileUtils.getRelativePath(SourceViewerTest.class).getPath() + "/property/save_bundle.properties");
        assertEquals(propertyFileTwo.getSourceViewer().getDocument().getNumberOfLines(), 3);

        propertyFileTwo.getSourceViewerDocument().getDocument().set("");
        propertyFileTwo.getSourceViewerDocument().saveDocument();*/
    }

    private String createPropertyEntry(final String entry) {
        return entry.concat(lineSeparator);
    }

    @Test
    public void numberOfLineTest() {
      /*  final PropertiesTextEditor propertyFile = readPropertyFile(FileUtils.getRelativePath(SourceViewerTest.class).getPath() + "/property/read_bundle.properties");
        assertEquals(propertyFile.getSourceViewerDocument().getNumberOfLines(), 38);*/
    }

    @Test
    public void fileModifiedEmptyDateTest() {
    /*    final PropertiesTextEditor propertyFile = readPropertyFile(FileUtils.getRelativePath(SourceViewerTest.class).getPath() + "/property/read_bundle.properties");
        final String modifiedDate = propertyFile.getSourceViewerDocument().getModificationTimeStamp(null);
        assertEquals(modifiedDate, "");*/
    }

    /* private PropertiesTextEditor readPropertyFile(final String fileName) {
        final Shell shell = new Shell(display, SWT.NONE);
        final Composite composite = new Composite(shell, SWT.NONE);
        IFile file;
        PropertiesTextEditor propertiesTextEditor = null;
        try {
            file = FileUtils.getResourceBundleRef(fileName, "TEST");
            propertiesTextEditor = new PropertiesTextEditor(composite, file);
        } catch (final CoreException e) {
            e.printStackTrace();
        }
        if (null != propertiesTextEditor) {
            Log.d(TAG, "CONTENT" + propertiesTextEditor.getSourceViewer().getDocument().get());
            Log.d(TAG, "" + propertiesTextEditor.getSourceViewer().getDocument().getNumberOfLines());
        }
        return propertiesTextEditor;
    }*/
}
