package com.nxj.application;

/**
 *
 * @author stuchl4n3k
 */
public abstract class Module {
    private Application context;

    protected void setContext(Application context) {
        this.context = context;
    }

    public Application getContext() {
        return context;
    }
}
