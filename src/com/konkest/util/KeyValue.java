package com.konkest.util;

import java.security.PrivateKey;

/**
 * Created by IntelliJ IDEA.
 * User: finalspy
 * Date: 23/01/11
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class KeyValue implements org.apache.commons.collections.KeyValue {
    private String key;
    private String value;

    public KeyValue(String key, String value){
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return this.key;  //To change body of implemented methods use File | Settings | File Templates.
    }
                    public Object getValue() {
                            return this.value;  //To change body of implemented methods use File | Settings | File Templates.
                        }
}
