package com.nxj.application.listeners;

import java.util.EventListener;

/**
 *
 * @author Felix
 */
public interface ShutdownListener extends EventListener {

    public void onShutdown();
}
