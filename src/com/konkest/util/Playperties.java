package com.konkest.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: finalspy Date: 22/01/11 Time: 00:36 To change
 * this template use File | Settings | File Templates.
 */
public class Playperties {

    private final Logger                logger              = Logger.getLogger(Playperties.class);
    private final LinkedList<ConfBlock> blocks              = new LinkedList<ConfBlock>();
    private static final Object         WARN_CONF_UNHANDLED = "Unhandled configuration line, not a comment nor a key/value line, skipping ...";

    public Playperties() {

    }

    public Playperties(final File file) {
        // TODO read file and add blocks

    }

    public Playperties(final String path) {
        // TODO try open file from path
        // TODO call Playperties(File) if success
    }

    public void load(final File file) {
        try {
            String strLine;
            boolean isTitle = true;
            ConfBlock tempBlock = null;
            // Open the file that is the first
            // command line parameter
            final FileInputStream fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(
                    in));
            this.blocks.clear();
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                if (strLine.trim().length() == 0) {
                    // vide on commencera un nouveau block
                    if (tempBlock != null) {
                        this.blocks.add(tempBlock);
                    }
                    tempBlock = new ConfBlock();
                    isTitle = true;
                } else if (strLine.startsWith("#")) {
                    // comment
                    if (isTitle) {
                        tempBlock.setTitle(strLine);
                    } else {
                        tempBlock.getComments().add(strLine);
                    }
                } else if (strLine.contains("=")) { // TODO ||
                                                    // strLine.contains(":")){
                    // key/value line
                    tempBlock.getProperties()
                            .add(new com.konkest.util.KeyValue(strLine
                                    .substring(0, strLine.indexOf("=")),
                                    strLine.substring(strLine.indexOf("=") + 1,
                                            strLine.length())));
                } else {
                    // unknown or unhandled
                    this.logger.warn(WARN_CONF_UNHANDLED);
                }
            }
            // Close the input stream
            in.close();

        } catch (final Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void load(final String path) {
        // TODO
    }

    public void save() {

    }

    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer();
        for (final ConfBlock block : this.blocks) {
            buff.append(block.toString());
        }
        return buff.toString();
    }
}
