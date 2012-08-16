package com.nxj.application;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Copyright 2012 Noblexity Advertising
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
/**
 * @author Petr Stuchl4n3k Stuchlik <stuchl4n3k@gmail.com>
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 * 
 * @nxj 0.1
 * @version 1.0
 */
public class Logging {

    /**
     * Simple formater
     */
    private static SimpleFormatter formater;

    /** ****************************** FACTORIES ****************************** */
    /**
     * Factory, enable file logger
     */
    public void enableFileLogger() {
        runFileLogger("", Level.FINE);
    }

    /**
     * Factory, enable file logger include logging package
     * @param loggingPackage 
     */
    public void enableFileLogger(String loggingPackage) {
        runFileLogger(loggingPackage, Level.FINE);
    }

    /**
     * Factory, enable file logger include logging package and level
     * @param loggingPackage
     * @param level 
     */
    public void enableFileLogger(String loggingPackage, Level level) {
        runFileLogger(loggingPackage, level);
    }

    /**
     * Factory, enable console logger
     */
    public void enableConsoleLogger() {
        runConsoleLogger("", Level.FINE);
    }

    /**
     * Factory, enable console logger include logging package
     * @param loggingPackage 
     */
    public void enableConsoleLogger(String loggingPackage) {
        runConsoleLogger(loggingPackage, Level.FINE);
    }

    /**
     * Factory, enable console logger include logging package and level
     * @param loggingPackage
     * @param level 
     */
    public void enableConsoleLogger(String loggingPackage, Level level) {
        runConsoleLogger(loggingPackage, level);
    }

    /**
     * Returns SimpleFormater [lazy loading]
     * @return 
     */
    public static SimpleFormatter getFormater() {
        if (formater == null) {
            formater = new SimpleFormatter();
        }

        return formater;
    }

    /**
     * Prepare main Logger for next usage
     * @param loggingPackage
     * @param level
     * @return 
     */
    private Logger prepareLogger(String loggingPackage, Level level) {
        Logger logger = Logger.getLogger("org.ipx.grabber");
        logger.setLevel(level);
        return logger;
    }

    /**
     * Run file logger 
     * @param loggingPackage
     * @param level 
     */
    private void runFileLogger(String loggingPackage, Level level) {
        Logger logger = prepareLogger(loggingPackage, level);

        try {
            FileHandler fh = new FileHandler(Application.getInstance().getConfig().getValue("logger.file", "./logger.log"), false);
            fh.setLevel(level);
            fh.setEncoding("UTF-8");
            logger.addHandler(fh);
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, "FileLogger error: {0}", ex.getMessage());
        }
    }

    /**
     * Run console logger
     * @param loggingPackage
     * @param level 
     */
    private void runConsoleLogger(String loggingPackage, Level level) {
        Logger logger = prepareLogger(loggingPackage, level);

        try {
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.FINEST);
            ch.setEncoding("UTF-8");
            logger.addHandler(ch);
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, "ConsoleLogger error: {0}", ex.getMessage());
        }
    }
}
