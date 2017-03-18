package test.org.eclipse.e4.babel.editor.text.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyFileResource;
import org.eclipse.e4.babel.editor.text.model.SourceEditor;
import org.junit.Before;
import org.junit.Test;


public class SourceEditorTest {

    private IPropertyResource fileResource;

    @Before
    public void beforeEach() throws IOException {
        fileResource = PropertyFileResource.create(new File("./source/bundle.properties"));

    }

    @Test
    public void localeNullTest() throws IOException {
        SourceEditor sourceEditor = SourceEditor.create(fileResource, null);
        assertNull(sourceEditor.getLocale());
    }

    @Test
    public void localeTest() {
        Locale locale = new Locale("de");
        SourceEditor sourceEditor = SourceEditor.create(fileResource, locale);
        assertNotNull(sourceEditor.getLocale());
        assertEquals(locale, sourceEditor.getLocale());
    }

    @Test
    public void contentNotNullTest() {
        SourceEditor sourceEditor = SourceEditor.create(fileResource, null);
        assertNotNull(sourceEditor.getContent());
    }

    @Test
    public void resourceTest() {
        SourceEditor sourceEditor = SourceEditor.create(fileResource, null);
        assertNotNull(sourceEditor.getResource());
        assertEquals(fileResource, sourceEditor.getResource());
    }

    @Test
    public void isResourceTest() throws IOException {
        SourceEditor sourceEditor = SourceEditor.create(fileResource, null);
        IPropertyResource otherFile = fileResource = PropertyFileResource.create(new File("./source/bundle.properties"));
        assertTrue(sourceEditor.isResource(otherFile));
    }

    @Test
    public void contentTest() {
        SourceEditor sourceEditor = SourceEditor.create(fileResource, null);
        sourceEditor.setContent("Hi this is a test");

        assertEquals("Hi this is a test", sourceEditor.getContent());
        assertFalse(sourceEditor.isCacheDirty());
    }
}
