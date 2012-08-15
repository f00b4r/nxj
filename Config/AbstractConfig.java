package com.nxj.system.config;

/**
 * @package com.njx.system.config
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 * @version 1.0
 */
abstract public class AbstractConfig {

    protected String name;

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
