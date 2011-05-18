package com.konkest.util;

import org.apache.commons.collections.KeyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finalspy
 * Date: 22/01/11
 * Time: 00:36
 * To change this template use File | Settings | File Templates.
 */
public class ConfBlock {
    private String title = "";
    private List<String> comments = new ArrayList<String>();
    private List<KeyValue> properties = new ArrayList<KeyValue>();

    public ConfBlock(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<KeyValue> getProperties() {
        return properties;
    }

    public void setProperties(List<KeyValue> properties) {
        this.properties = properties;
    }
    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("# " + title);
        return buff.toString();
    }
}
