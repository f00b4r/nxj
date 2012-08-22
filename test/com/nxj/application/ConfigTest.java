package com.nxj.application;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Felix
 */
public class ConfigTest {

    public ConfigTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void loadBundle() {
        Config config = Config.getConfigBundle("com.nxj.bundle");
        assertEquals("bar", config.getValue("foo"));
        assertEquals("foobar", config.getValue("foo.bar"));
    }

    @Test
    public void loadPropertiesFile() {
        Config config = Config.getConfig("test/com/nxj/bundle.properties");
        assertEquals("bar", config.getValue("foo"));
        assertEquals("foobar", config.getValue("foo.bar"));
    }

    @Test
    public void values() {
        Config config = Config.getConfig("test/com/nxj/bundle.properties");
        assertEquals("bar", config.getValue("foo"));
        assertEquals("foobar", config.getValue("foo.bar"));
        assertNull(config.getValue("foo.bar.bar"));
        assertSame("20", config.getValue("foo.bar.bar", "20"));
    }

    @Test
    public void store() {
        Config config = Config.getConfig("test/com/nxj/bundle.properties");
        config.setValue("foo.bar.foo", "foobarfoo");
        config.store("test/com/nxj/bundle.properties");
    }
}
