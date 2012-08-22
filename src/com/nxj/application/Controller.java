package com.nxj.application;

import com.nxj.application.listeners.ShutdownListener;
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
