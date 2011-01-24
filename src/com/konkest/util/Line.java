package com.konkest.util;

/**
 * Created by IntelliJ IDEA.
 * User: finalspy
 * Date: 24/01/11
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */
public class Line {
    private boolean comment;
    private boolean title;
    private String content;
    private String key;
    private String value;
    private int length;

    public String getContent() {
        return this.content;
    }

    public boolean isComment() {
        return this.comment;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public boolean isTitle() {
        return this.title;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public int getLength() {
        return this.length;

    }

    public Line(String line) {
        this.comment = line.startsWith("#");
        this.content = (this.comment) ? line.substring(1) : line;
        this.length = this.content.length();
        if (line.contains("=")) {
            this.key = line.substring(0, line.indexOf("="));
            this.value = line.substring(line.indexOf("=") + 1, line.length());
        }
    }

    public String toString() {
        return "(" + content + ")\n";
    }
}