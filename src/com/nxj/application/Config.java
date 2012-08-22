package com.nxj.application;

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
 * Copyright 2012 Noblexity Advertising
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
/**
 * @author Petr Stuchl4n3k Stuchlik <stuchl4n3k@gmail.com>
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 *
 * @nxj 0.1
 * @version 1.0
 */
public class Config {

    /**
     * Constant
     */
    private static final int STORE_TYPE_NORMAL = 1;
    private static final int STORE_TYPE_XML = 2;
    private static final String DEFAULT_NAME = "Config";
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(Config.class.getName());
    /**
     * Fields
     */
    protected Properties properties;
    protected int storeType;
    protected String name;
    protected String filename;

    /**
     * Private constructor
     *
     * @param name
     * @param storeType
     */
    private Config(String name, int storeType) {
        this.name = name;
        this.storeType = storeType;
        properties = new Properties();
    }

    /**
     * Private constructor
     *
     * @param filename
     * @param name
     * @param storeType
     */
    private Config(String filename, String name, int storeType) {
        this(name, storeType);
        this.filename = filename;
        tryLoad(filename);
    }

    /**
     * Private constructor
     *
     * @param filename
     * @param name
     * @param storeType
     */
    private Config(ResourceBundle bundle, String name, int storeType) {
        this(name, storeType);
        tryLoad(bundle);
    }

    /**
     * Factory
     *
     * @param filename
     * @return
     */
    public static Config getConfigBundle(String filename) {
        Config config = new Config(ResourceBundle.getBundle(filename), DEFAULT_NAME, STORE_TYPE_NORMAL);
        config.setFilename(filename);
        return config;
    }

    /**
     * Factory
     *
     * @param bundle
     * @return
     */
    public static Config getConfig(ResourceBundle bundle) {
        Config config = new Config(bundle, DEFAULT_NAME, STORE_TYPE_NORMAL);
        config.setFilename("./" + DEFAULT_NAME);
        return config;
    }

    /**
     * Factory
     *
     * @param filename
     * @return
     */
    public static Config getConfig(String filename) {
        return new Config(filename, DEFAULT_NAME, STORE_TYPE_NORMAL);
    }

    /**
     * Factory
     *
     * @param filename
     * @param name
     * @return
     */
    public static Config getConfig(String filename, String name) {
        return new Config(filename, name, STORE_TYPE_NORMAL);
    }

    /**
     * Factory
     *
     * @param filename
     * @param storeType
     * @return
     */
    public static Config getConfig(String filename, int storeType) {
        return new Config(filename, DEFAULT_NAME, storeType);
    }

    /**
     * Factory
     *
     * @param filename
     * @param name
     * @param storeType
     * @return
     */
    public Config getConfig(String filename, String name, int storeType) {
        return new Config(filename, name, storeType);
    }

    /**
     * Gets name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets filename
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets filename
     *
     * @param name
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Gets value
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets value or defaultValue
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getValue(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Sets key => value
     *
     * @param key
     * @param value
     */
    public void setValue(String key, Object value) {
        properties.put(key, value);
    }

    /**
     * Save settings via filename
     */
    public void store() {
        store(filename);
    }

    /**
     * Save settings
     *
     * @param filename
     */
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
     *
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
     *
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
