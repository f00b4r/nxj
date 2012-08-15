package com.nxj.system.config;

/**
 * @package com.njx.system.config
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 * @version 1.0
 */
public interface ISettable extends IGettable {

    /**
     * Sets key => value
     * @param key
     * @param value 
     */
    public void setValue(String key, Object value);
}
