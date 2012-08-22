package com.nxj.nframe;

import com.nxj.application.Application;
import com.nxj.application.listeners.ShutdownListener;

/**
 *
 * @author Felix
 */
public class TestMain implements ShutdownListener {

    public static void main(String[] args) {

        TestMain main = new TestMain();

        //main.defaultFrameTest();
        //main.shutdownFrameTest();
        main.onCloseFrameTest();

    }

    @Override
    public void onShutdown() {
        System.out.println("Tohle je shutdownlistener ");
    }

    private void defaultFrameTest() {
        // Show frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DefaultFrameTest frame = new DefaultFrameTest();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }

    private void shutdownFrameTest() {
        Application app = Application.getInstance();

        // Register shutdownlistener
        app.addShutdownListener(this);

        // Show frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ShutdownFrameTest frame = new ShutdownFrameTest();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }

    private void onCloseFrameTest() {
        // Show frame
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                OnCloseFrameTest frame = new OnCloseFrameTest();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }
}
