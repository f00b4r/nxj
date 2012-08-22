package com.nxj.application;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

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
public class NFrame extends JFrame {

    /**
     * Override default close operation
     */
    protected static final int CLOSE_OPERATION = EXIT_ON_CLOSE;
    
    /**
     * Constant for future shutdown
     */
    protected static boolean SHUTDOWN = false;

    /**
     * Not used constructor
     *
     * @throws HeadlessException
     */
    public NFrame() throws HeadlessException {
        run();
    }

    /**
     * @param gc GraphicsConfiguration
     */
    public NFrame(GraphicsConfiguration gc) {
        super(gc);
        run();
    }

    /**
     * @param title
     * @throws HeadlessException
     */
    public NFrame(String title) throws HeadlessException {
        super(title);
        run();
    }

    /**
     * @param title
     * @param gc
     */
    public NFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        run();
    }

    /**
     * ************************** DELEGATING **************************
     */
    /**
     * Delegate shema
     */
    private void run() {

        // Sets do nothing on close
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Override close operation
        delegateClose();

        // Override init operation
        delegateInit();
    }

    /**
     * Delegate close
     */
    private void delegateClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    /**
     * Delegate init
     */
    private void delegateInit() {
        // Prepare data, models, etc
        beforeInit();

        // Attache components, models, data, etd
        init();

        // Fixes, serializes
        afterInit();
    }

    /**
     * ************************** PREPARED **************************
     */
    /**
     * Prepared close operation
     */
    protected void close() {
        // Call handler onClose()
        onClose();
        
        // Default close operation
        setDefaultCloseOperation(CLOSE_OPERATION);
        
        // Shut application shutdown?
        if (SHUTDOWN) {
            shutdown();
        }
    }

    /**
     * Prepared application shutdown shortcut
     */
    protected void shutdown() {
        // Application shutdown
        Application.getInstance().shutdown();
    }

    /**
     * Prepared before init
     */
    protected void beforeInit() {
    }

    /**
     * Prepared init
     */
    protected void init() {
    }

    /**
     * Prepared after init
     */
    protected void afterInit() {
    }

    /**
     * Custom NFrame close method,
     * for saving, storing, etc..
     */
    protected void onClose() {
        // nothing
    }
}
