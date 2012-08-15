package com.nxj.system.config;

/**
 * @package com.njx.system.config
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 * @version 1.0
 */
public interface IGettable {

    /**
     * Gets value
     * @param key
     * @return 
     */
    public String getValue(String key);

    /**
     * Gets value or default value
     * @param key
     * @param defaultValue
     * @return 
     */
    public Object getValue(String key, String defaultValue);
}
