package test.org.eclipse.e4.babel.core.test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.babel.core.internal.resource.ResourceManager;
import org.eclipse.e4.babel.editor.text.file.IPropertyResource;
import org.eclipse.e4.babel.editor.text.file.PropertyFileResource;
import org.eclipse.e4.babel.editor.text.file.PropertyIFileResource;
import org.junit.Before;
import org.junit.Test;


public class ResourceManagerExternalTest {

    private ResourceManager manager;
    private IPropertyResource fileResource;
    private PropertyIFileResource iFileResource;

    @Before
    public void beforeEach() throws IOException {
        manager = new ResourceManager();
        fileResource = PropertyFileResource.create(new File("./project/bundle/bundle.properties"));
    }

    @Test
    public void initManagerTest() throws CoreException, IOException {
        CompletableFuture<Void> async = manager.init(fileResource);
        pauseSeconds(1);
        assertThat(async.isDone(), is(true));
    }

    @Test
    public void containResourceTest() throws CoreException, IOException {
        CompletableFuture<Void> async = manager.init(fileResource);
        pauseSeconds(1);
        assertThat(async.isDone(), is(true));
        IPropertyResource newResource = PropertyFileResource.create(new File("./project/bundle/bundle.properties"));
        boolean containsResource = manager.containsResource(newResource);
        assertTrue(containsResource);
    }

    @Test
    public void sourceEditorTest() throws CoreException, IOException {
        CompletableFuture<Void> async = manager.init(fileResource);
        pauseSeconds(1);
        assertThat(async.isDone(), is(true));
        assertEquals(3, manager.getSourceEditors().size());
    }

    @Test
    public void localeTest() throws CoreException, IOException {
        CompletableFuture<Void> async = manager.init(fileResource);
        pauseSeconds(1);
        assertThat(async.isDone(), is(true));
        System.out.println(manager.getLocales().toString());

        assertEquals(3, manager.getLocales().size());
    }

    @Test
    public void localeDefaultTest() throws CoreException, IOException {
        CompletableFuture<Void> async = manager.init(fileResource);
        pauseSeconds(1);
        assertThat(async.isDone(), is(true));
        System.out.println(manager.getLocales().toString());

        assertEquals(3, manager.getLocales().size());
        assertNull(manager.getLocales().get(0));
    }

    private void pauseSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
