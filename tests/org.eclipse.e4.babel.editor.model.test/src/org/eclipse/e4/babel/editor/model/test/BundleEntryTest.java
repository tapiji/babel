package org.eclipse.e4.babel.editor.model.test;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.eclipse.e4.babel.editor.model.bundle.BundleEntry;
import org.junit.BeforeClass;
import org.junit.Test;

public class BundleEntryTest {

	
    @BeforeClass
    public static void setup() {
    }

    @Test
    public void createBundleEntryTest() {
    	BundleEntry entry = BundleEntry.create("btn.ok", "OK", "", false);
    	
    	entry.setLocale(new Locale("en", "US"));
    	assertEquals("OK", entry.getValue());
    	assertEquals(false, entry.isCommented());
    	assertEquals("btn.ok",entry.getKey());
    	assertEquals("", entry.getComment());
    	assertEquals("en", entry.getLocale().getLanguage());
    }
    
    @Test
    public void createBundleEntryWithCommentTest() {
    	BundleEntry entry = BundleEntry.create("hello", "hallo", "comment", true);
    	assertEquals("hallo", entry.getValue());
    	assertEquals(true, entry.isCommented());
    	assertEquals("hello",entry.getKey());
    	assertEquals("comment", entry.getComment());
    }
    
    @Test
    public void createBundleEntryWithNullTest() {
    	BundleEntry entry = BundleEntry.create(null, null, null, true);
    	assertEquals("", entry.getValue());
    	assertEquals(true, entry.isCommented());
    	assertEquals("",entry.getKey());
    	assertEquals("", entry.getComment());
    }
    
    @Test
    public void bundleEntryToStringTest() {
    	 String expected = "BundleEntry [key=hello, value=hallo, locale=de_D, comment=comment, commented=true]";
    	 BundleEntry entry = BundleEntry.create("hello", "hallo", "comment", true);
    	 entry.setLocale(new Locale("de", "D"));
    	 
    	 assertEquals(expected, entry.toString());
    }
}
