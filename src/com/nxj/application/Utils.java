package com.nxj.application;

import java.io.File;
import java.lang.reflect.Field;

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
public class Utils {

    /**
     * Loads a DLL using System.loadLibrary on runtime.
     *
     * @param pathToLib A path to library - absolute or relative (without dll
     * extension).
     *
     * @return True on successful load.
     */
    public static boolean loadLib(String pathToLib) {
        File libFile = new File(pathToLib);
        String libDir = libFile.getParent();
        String libName = libFile.getName();

        try {
            // Dirty, but useful hack to set up Java library path on runtime.
            System.setProperty("java.library.path", libDir);
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);

            System.loadLibrary(libName);
            return true;
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
