package com.nxj.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

/**
 * @package com.njx.system.config
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 * @version 1.0
 *
 * @todo
 */
public class PropertyChangeManager {

    private transient PropertyChangeSupport changeSupport;
    private static final Logger logger = Logger.getLogger(PropertyChangeManager.class.getName());

    public PropertyChangeManager() {
        changeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Fire property change listener
     * @param propertyName
     * @param oldValue
     * @param newValue
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if ((oldValue == null && newValue == null) || oldValue == newValue) {
            return;
        }
        changeSupport.firePropertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
    }

    /**
     * Adds a property change listener width String key
     * @param propertyName
     * @param listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Adds a property change listener
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes the property change listner by propertyName
     * @param propertyName
     * @param listener
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }
}
