package com.konkest.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.KeyValue;

/**
 * Created by IntelliJ IDEA. User: finalspy Date: 22/01/11 Time: 00:36 To change
 * this template use File | Settings | File Templates.
 */
public class ConfBlock {
    private String         title      = "";
    private List<String>   comments   = new ArrayList<String>();
    private List<KeyValue> properties = new ArrayList<KeyValue>();

    public ConfBlock() {

    }

    public List<String> getComments() {
        return this.comments;
    }

    public List<KeyValue> getProperties() {
        return this.properties;
    }

    public String getTitle() {
        return this.title;
    }

    public void setComments(final List<String> comments) {
        this.comments = comments;
    }

    public void setProperties(final List<KeyValue> properties) {
        this.properties = properties;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer();
        buff.append("# " + this.title);
        return buff.toString();
    }
}
