package com.konkest.util;

/**
 * Created by IntelliJ IDEA. User: finalspy Date: 24/01/11 Time: 22:49 To change this template use File |
 * Settings | File Templates.
 */
public class Line {
    private final boolean comment;
    private boolean       title;
    private boolean       blockEnd;
    private final boolean separator;
    private String        content;
    private String        key;
    private String        value;
    private final int     length;
    private int           id;

    public Line(final String line) {
        this.comment = line.startsWith("#");
        this.separator = line.contains("~~~~~");
        this.content = (this.comment) ? line.substring(1) : line;
        this.length = this.content.length();
        if (line.contains("=")) {
            this.key = (this.comment) ? line.substring(1, line.indexOf("=")) : line.substring(0, line.indexOf("="));
            this.value = line.substring(line.indexOf("=") + 1, line.length());
        }
    }

    public String getContent() {
        return this.content;
    }

    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public int getLength() {
        return this.length;

    }

    public String getValue() {
        return this.value;
    }

    /**
     * @return the blockEnd
     */
    public boolean isBlockEnd() {
        return this.blockEnd;
    }

    public boolean isComment() {
        return this.comment;
    }

    public boolean isSeparator() {
        return this.separator;
    }

    public boolean isTitle() {
        return this.title;
    }

    /**
     * @param blockEnd
     *            the blockEnd to set
     */
    public void setBlockEnd(final boolean blockEnd) {
        this.blockEnd = blockEnd;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    public void setTitle(final boolean title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return ((this.comment) ? "#" : "") + this.content;
    }
}
