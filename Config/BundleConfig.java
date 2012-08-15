package com.nxj.system.config;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @package com.njx.system.config
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 * @version 1.0
 */
public class BundleConfig extends AbstractConfig implements IGettable {

    protected static final Logger logger = Logger.getLogger(BundleConfig.class.getName());
    protected ResourceBundle bundle;

    /**
     * Private constructor
     * @param bundlePath 
     */
    private BundleConfig(String bundlePath) {
        bundle = ResourceBundle.getBundle(bundlePath);
    }
    
    /**
     * Factory
     * @param budlePath 
     */
    public static BundleConfig getConfig(String budlePath) {
        return new BundleConfig(budlePath);
    }

    @Override
    public String getValue(String key) {
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        }
        logger.log(Level.CONFIG, "Key {0} not found in bundle", key);
        return null;
    }

    @Override
    public Object getValue(String key, String defaultValue) {
        String v = getValue(key);
        return (v != null ? v : defaultValue);
    }
}
