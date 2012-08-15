package com.nxj.application;

import java.io.File;
import java.lang.reflect.Field;

/**
 *
 * @author stuchl4n3k
 */
public class Utils {

    /**
     * Loads a DLL using System.loadLibrary on runtime.
     *
     * @param pathToLib A path to library - absolute or relative
     * (without dll extension).
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
