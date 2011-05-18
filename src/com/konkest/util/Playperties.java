package com.konkest.util;

import org.apache.commons.collections.KeyValue;
import org.apache.log4j.Logger;


import java.io.*;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: finalspy
 * Date: 22/01/11
 * Time: 00:36
 * To change this template use File | Settings | File Templates.
 */
public class Playperties {

    private Logger logger = Logger.getLogger(Playperties.class);
    private LinkedList<ConfBlock> blocks = new LinkedList<ConfBlock>();
    private static final Object WARN_CONF_UNHANDLED = "Unhandled configuration line, not a comment nor a key/value line, skipping ...";

    public Playperties() {

    }

    public Playperties(File file) {
        // TODO read file and add blocks

    }

    public Playperties(String path) {
        // TODO try open file from path
        // TODO call Playperties(File) if success
    }

    public void load(String path) {
	    // TODO 
    }

    public void load(File file) {
        try {
            String strLine;
            boolean isTitle = true;
            ConfBlock tempBlock = null;
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            blocks.clear();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                if (strLine.trim().length() == 0) {
                    // vide on commencera un nouveau block
                    if (tempBlock != null)
                        blocks.add(tempBlock);
                    tempBlock = new ConfBlock();
                    isTitle = true;
                } else if (strLine.startsWith("#")) {
                    // comment
                    if (isTitle){
                        tempBlock.setTitle(strLine);
                    } else {
                        tempBlock.getComments().add(strLine);
                    }
                } else if (strLine.contains("=")) { // TODO || strLine.contains(":")){
                    // key/value line
                    tempBlock.getProperties().add(new com.konkest.util.KeyValue(strLine.substring(0, strLine.indexOf("=")),strLine.substring(strLine.indexOf("=") + 1, strLine.length())));
                 } else {
                    // unknown or unhandled
                   logger.warn(WARN_CONF_UNHANDLED);
                }
            }
            //Close the input stream
            in.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void save() {

    }

    public String toString(){
        StringBuffer buff = new StringBuffer();
        for(ConfBlock block : blocks){
            buff.append(block.toString());
        }
        return buff.toString();
    }
}

