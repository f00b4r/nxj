package com.nxj.application;

import com.nxj.application.listeners.ShutdownListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stuchl4n3k
 */
public abstract class Controller implements ShutdownListener {

    private Application context;
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public Controller() {
        logger.log(Level.INFO, getClass() + " instantiated");
    }

    public void onBootstrap() {
    }

    @Override
    public void onShutdown() {

    }

    public Application getContext() {
        return context;
    }

    void setContext(Application context) {
        this.context = context;
    }

}
