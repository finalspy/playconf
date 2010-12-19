package controllers.playconf;

import play.Play;
import play.mvc.Controller;

import java.io.*;
import java.util.*;


public class PlayConf extends Controller {
    public static boolean confReadableGlobal = false;
    public static String separator = System.getProperty("file.separator");

    /**
     * The framework ID
     */
    public static String id;

    private static List<Object> fileperties = new ArrayList<Object>();
    private static Map<String, Integer> keylinemap = new HashMap<String, Integer>();
    private static Map<String, String> keyvaluemap = new HashMap<String, String>();

    public static final String APPLICATION_CONF = Play.applicationPath + separator + "conf"
                    + separator + "application.conf";
    public static final String APPLICATION_CONF_BAK = APPLICATION_CONF + ".bak";

    public static void index() {

        // Check if previous report exist
        if (!confReadableGlobal) {
            File f = new File(APPLICATION_CONF);
             if (f.exists()) {
                confReadableGlobal = true;
            }
        }
        if (confReadableGlobal) {
            readPlayperties();
            Set props = keyvaluemap.entrySet(); //configuration.entrySet();
            render(props);
        }
    }


    public static void save() {

        Play.stop();
        System.out.println("~ Application Stopped");

        System.out.println("~ Saving configuration ...");
        // backup old file
        File old = new File(APPLICATION_CONF_BAK);
        old.delete();

        File cur = new File(APPLICATION_CONF);
        cur.renameTo(old);

        // pour chaque key si value changée alors set
        for (Map.Entry<String, String[]> e : params.all().entrySet()) {
            if (!e.getValue()[0].equals(keyvaluemap.get(e.getKey()))) {
                keyvaluemap.put(e.getKey(), e.getValue()[0]);
                fileperties.set(keylinemap.get(e.getKey()), e.getKey() + "=" + e.getValue()[0]);
            }
        }
        writePlayperties();
        System.out.println("~ Configuration Saved");

        Play.start();
        System.out.println("~ Application Restarted");
        index();
    }


    static void readPlayperties() {
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(APPLICATION_CONF);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String key;
            String value;
            fileperties.clear();
            boolean isTitle = true;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                if (strLine.length() == 0) {
                    // vide
                    fileperties.add(strLine);
                } else if (strLine.startsWith("#")) {
                    // comment
                    fileperties.add(strLine);
                } else if (strLine.contains("=")) { // TODO || strLine.contains(":")){
                    // key/value line
                    fileperties.add(strLine);
                    key = strLine.substring(0, strLine.indexOf("="));
                    value = strLine.substring(strLine.indexOf("=") + 1, strLine.length());
                    // key/line Map
                    keylinemap.put(key, fileperties.size() - 1);
                    // key/value Map
                    keyvaluemap.put(key, value);
                } else {
                    // unknown or unhandled
                    fileperties.add(strLine);
                }
            }
            //Close the input stream
            in.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    static void writePlayperties() {
        try {
            // Open an output stream
            File f = new File(APPLICATION_CONF);

            FileOutputStream fout = new FileOutputStream(f);
            fout.flush();

            // Print a line of text
            PrintStream p = new PrintStream(fout);
            p.flush();

            for (Object o : fileperties) {
                p.println(o);
            }

            p.close();
            // Close our output stream
            fout.close();
            
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

}
