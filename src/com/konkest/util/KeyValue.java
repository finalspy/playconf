package com.konkest.util;

/**
 * @author finalspy
 */
public class KeyValue implements org.apache.commons.collections.KeyValue {
    private final String key;
    private final String value;

    public KeyValue(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Object getKey() {
        return this.key; // To change body of implemented methods use File |
                         // Settings | File Templates.
    }

    @Override
    public Object getValue() {
        return this.value; // To change body of implemented methods use File |
                           // Settings | File Templates.
    }
}
