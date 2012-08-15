package com.nxj.application;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felix
 */
public class Config implements Serializable {

    protected static final String DEFAULT_CONFIG_FILE = "noblej.config";
    protected ResourceBundle application;
    protected ResourceBundle defaults;
    protected Properties properties;
    private static final Logger logger = Logger.getLogger(Config.class.getName());
    private transient PropertyChangeSupport changeSupport;

    public Config(String appBundlePath, String defBundlePath) {
        application = ResourceBundle.getBundle(appBundlePath);
        defaults = ResourceBundle.getBundle(defBundlePath);
        properties = new Properties();
        changeSupport = new PropertyChangeSupport(this);
        startup();
    }

    /**
     * Adds a property change listener width String key
     * @param propertyName
     * @param listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Adds a property change listener
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes the property change listner by propertyName
     * @param propertyName
     * @param listener
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Fire property change listener
     * @param propertyName
     * @param oldValue
     * @param newValue
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if ((oldValue == null && newValue == null) || oldValue == newValue) {
            return;
        }
        changeSupport.firePropertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
    }

    /**
     * Gets a application config value
     * @param key
     * @return
     */
    public String getValue(String key) {
        if (application.containsKey(key)) {
            return application.getString(key);
        }
        logger.log(Level.CONFIG, "Key {0} not found in bundle", key);
        return null;
    }

    /**
     * Gets a custom setting value
     * @param key
     * @return String or null
     */
    public String getProperty(String key) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        logger.log(Level.CONFIG, "Key {0} not found in properties", key);
        return null;
    }

    /**
     * Gets a custom or default setting value
     * @param key
     * @return String
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Sets a Object o to String key
     * @param String key
     * @param Object o
     */
    public void setProperty(String key, Object o) {
        properties.put(key, o);
    }

    /**
     * Loads config from properly file
     * @param filename
     */
    public void load(String filename) {
        try {
            // Check if config file exists
            File configFile = new File(filename);
            if (configFile.exists()) {
                // if yes, load custom settings
                properties.load(new FileInputStream(filename));
            }

        } catch (IOException e) {
            logger.log(Level.CONFIG, "Load fail: {0}", e.getMessage());
        }
    }

    /**
     * Process when application starts
     */
    private void startup() {
        logger.logp(Level.CONFIG, "Config", "startup", "application config startup");
        startup(new File(DEFAULT_CONFIG_FILE));
    }

    /**
     * Process when application starts
     * @param configFile
     */
    private void startup(String configFile) {
        logger.logp(Level.CONFIG, "Config", "startup", "application config startup", new Object[]{configFile});
        startup(new File(configFile));
    }

    /**
     * Process when application starts
     * @param configFile
     */
    private void startup(File configFile) {
        logger.logp(Level.CONFIG, "Config", "startup", "application config startup", new Object[]{configFile});
        if (configFile.exists()) {
            load(configFile.getAbsolutePath());
        } else {
            loadDefaults(defaults);
        }
    }

    /**
     * Write config to properly file
     * @param filename
     */
    public void store(String filename) {
        try {
            try (FileOutputStream f = new FileOutputStream(filename)) {
                properties.store(f, "IPxUtils settings");
            }
        } catch (IOException e) {
            logger.log(Level.CONFIG, "Store fail: {0}", e.getMessage());
        }
    }

    /**
     * Fill first defaults data
     * @param resource
     */
    private void loadDefaults(ResourceBundle resource) {
        logger.config("Loading defaults settings");
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            logger.log(Level.CONFIG, "Load config key: {0}", key);
            properties.put(key, resource.getString(key));
            firePropertyChange(key, null, resource.getString(key));
        }
    }

    public void reload() {
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            firePropertyChange(key, null, properties.getProperty(key));
        }
    }
}
