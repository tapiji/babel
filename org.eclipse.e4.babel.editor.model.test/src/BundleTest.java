import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Locale;

import org.eclipse.e4.babel.editor.model.bundle.Bundle;
import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.junit.BeforeClass;
import org.junit.Test;

public class BundleTest {
	
    @BeforeClass
    public static void setup() {
    }
    
    @Test
    public void createBundleCommentTest() {
    	Bundle bundle = Bundle.create();
    	bundle.setComment("comment");
    	
    	assertEquals("comment", bundle.getComment());
    	assertNull(bundle.getLocale());
    }
    
    @Test 
    public void createBundleLocaleTest() {
    	Bundle bundle = Bundle.create();
    	bundle.setLocale(new Locale("de","D"));
    	
    	assertEquals("de_D", bundle.getLocale().toString());
    	assertNull(bundle.getComment());
    }
    
    @Test
    public void getEmptyBundleEntriesTest() {
    	Bundle bundle = Bundle.create();
    	assertNotNull(bundle.getBundleEntries());
    }
    
    @Test
    public void getBundleEntryByKeyTest() {
    	Bundle bundle = Bundle.create();
    	assertNull(bundle.getBundleEntry(""));
    }
    
    @Test
    public void bundleEntriesSizeTest() {
    	Bundle bundle = Bundle.create();
    	assertEquals(0, bundle.getBundleEntriesCount());
    }
    
    @Test
    public void addBundleEntryToBundleTest() {
    	Bundle bundle = Bundle.create();
    	BundleEntry entry = BundleEntry.create("btn.ok", "OK");
    	bundle.addBundleEntry(entry);
    	assertEquals(1, bundle.getBundleEntriesCount());
    }
    
    @Test
    public void addBundleEntryWithEmptyKeyTest() {
    	Bundle bundle = Bundle.create();
        BundleEntry entry = BundleEntry.create(" ", "OK");
    	bundle.addBundleEntry(entry);
    	assertEquals(0, bundle.getBundleEntriesCount());
    }
    
    @Test
    public void addBundleEntryTwiceTest() {
    	Bundle bundle = Bundle.create();
    	BundleEntry entry = BundleEntry.create("btn.ok", "OK");
    	bundle.addBundleEntry(entry);
    	bundle.addBundleEntry(entry);
    	assertEquals(1, bundle.getBundleEntriesCount());
    }
    
    @Test
    public void removeBundleEntryTest() {
    	Bundle bundle = Bundle.create();
    	BundleEntry entry = BundleEntry.create("btn.ok", "OK");
    	bundle.addBundleEntry(entry);
    	assertEquals(1, bundle.getBundleEntriesCount());
    	bundle.removeBundleEntry(entry);
    	assertEquals(0, bundle.getBundleEntriesCount());
    }
    
    @Test
    public void removeBundleWithEmptyKeyTest() {
    	Bundle bundle = Bundle.create();
    	BundleEntry entry = BundleEntry.create("", "OK");
    	bundle.removeBundleEntry(entry);
    	assertEquals(0, bundle.getBundleEntriesCount());
    }
    
    @Test
    public void renameBundleEntryTest() {
    	String expected = "btn.nok";
    	Bundle bundle = Bundle.create();
    	BundleEntry entry = BundleEntry.create("btn.ok", "OK");
    	bundle.addBundleEntry(entry);
    	assertEquals(1, bundle.getBundleEntriesCount());
    	bundle.renameBundleEntryKey(entry.getKey(), expected);
    	BundleEntry renamedEntry = bundle.getBundleEntry(expected);
    	assertEquals(expected, renamedEntry.getKey());
    }
    
    @Test
    public void addNullBundleEntryTest() {
    	Bundle bundle = Bundle.create();
    	bundle.addBundleEntry(null);
    }
}
