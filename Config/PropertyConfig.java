package com.nxj.system.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @package com.njx.system.config
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 * @version 1.0
 */
public class PropertyConfig extends AbstractConfig implements ISettable, IStorable {

    private static final int STORE_TYPE_NORMAL = 1;
    private static final int STORE_TYPE_XML = 2;
    private static final String DEFAULT_NAME = "PropertyConfig";
    protected static final Logger logger = Logger.getLogger(PropertyConfig.class.getName());
    private Properties properties;
    private int storeType;

    /**
     * Private constructor
     * @param name
     * @param storeType 
     */
    private PropertyConfig(String name, int storeType) {
        setName(name);
        this.storeType = storeType;
        properties = new Properties();
    }

    /**
     * Private constructor
     * @param filename
     * @param name
     * @param storeType 
     */
    private PropertyConfig(String filename, String name, int storeType) {
        this(name, storeType);
        tryLoad(filename);
    }

    /**
     * Private constructor
     * @param filename
     * @param name
     * @param storeType 
     */
    private PropertyConfig(ResourceBundle bundle, String name, int storeType) {
        this(name, storeType);
        tryLoad(bundle);
    }

    /**
     * Factory 
     * @param filename
     * @return 
     */
    public static PropertyConfig getConfigBundle(String filename) {
        return new PropertyConfig(ResourceBundle.getBundle(filename), DEFAULT_NAME, STORE_TYPE_NORMAL);
    }

    /**
     * Factory 
     * @param filename
     * @return 
     */
    public static PropertyConfig getConfig(String filename) {
        return new PropertyConfig(filename, DEFAULT_NAME, STORE_TYPE_NORMAL);
    }

    /**
     * Factory 
     * @param filename
     * @param name
     * @return 
     */
    public static PropertyConfig getConfig(String filename, String name) {
        return new PropertyConfig(filename, name, STORE_TYPE_NORMAL);
    }

    /**
     * Factory
     * @param filename
     * @param storeType
     * @return 
     */
    public static PropertyConfig getConfig(String filename, int storeType) {
        return new PropertyConfig(filename, DEFAULT_NAME, storeType);
    }

    /**
     * Factory
     * @param filename
     * @param name
     * @param storeType
     * @return 
     */
    public PropertyConfig getConfig(String filename, String name, int storeType) {
        return new PropertyConfig(filename, name, storeType);
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    @Override
    public Object getValue(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public void setValue(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public void store(String filename) {
        try {
            try (FileOutputStream f = new FileOutputStream(filename)) {
                switch (storeType) {
                    case STORE_TYPE_XML:
                        properties.storeToXML(f, getName());
                        break;
                    default:
                        properties.store(f, getName());
                        break;
                }

            }
        } catch (IOException e) {
            logger.log(Level.CONFIG, "Store fail: {0}", e.getMessage());
        }
    }

    /**
     * Load settings from file
     * @param filename 
     */
    private void tryLoad(String filename) {
        try {
            // Check if config file exists
            File configFile = new File(filename);
            if (configFile.exists()) {
                // if yes, load custom settings
                properties.load(new FileInputStream(filename));
            }

        } catch (IOException e) {
            logger.log(Level.CONFIG, "Load from file failed: {0}", e.getMessage());
        }
    }

    /**
     * Load settings from bundle
     * @param filename 
     */
    private void tryLoad(ResourceBundle bundle) {
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, bundle.getString(key));
        }
    }
}
