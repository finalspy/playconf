package controllers.playconf;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Play;
import play.mvc.Controller;

import com.konkest.util.Line;

public class PlayConf extends Controller {
    public static boolean               confReadableGlobal = false;
    public static String                separator          = System.getProperty("file.separator");

    /**
     * The framework ID
     */
    public static String                id;
    private static List<Object>         fileperties        = new ArrayList<Object>();
    private static Map<String, Integer> keylinemap         = new HashMap<String, Integer>();
    private static Map<String, String>  keyvaluemap        = new HashMap<String, String>();
    private static List<Line>           file               = new ArrayList<Line>();

    public static final String          APP_PATH           = Play.applicationPath + separator + "conf" + separator;
    public static final String          APP_CONF           = APP_PATH + "application.conf";
    public static final String          APP_MESSAGES       = APP_PATH + "messages";
    public static final String          APP_ROUTES         = APP_PATH + "routes";
    public static final String          APP_CONF_BAK       = APP_CONF + ".bak";

    private static void controlPlayperties() {
        // pour chaque key si value changée alors set
        for (final Map.Entry<String, String[]> e : params.all().entrySet()) {
            if ((e.getValue() != null) && (e.getValue()[0] != null) && (keyvaluemap != null)
                    && !e.getValue()[0].equals(keyvaluemap.get(e.getKey()))) {
                keyvaluemap.put(e.getKey(), e.getValue()[0]);
                // FIXME NPE here keylinemap is empty
                if (null != keylinemap.get(e.getKey())) {
                    fileperties.set(keylinemap.get(e.getKey()), e.getKey() + "=" + e.getValue()[0]);
                }
                else {
                    fileperties.add(e.getKey() + "=" + e.getValue()[0]);
                }
            }
        }
    }

    public static void index() {
        preparePage(APP_CONF);
    }

    public static void messages() {
        preparePage(APP_MESSAGES);
    }

    private static void preparePage(final String path) {
        // Check if previous report exist
        if (confReadableGlobal |= new File(path).exists()) {
            readPlayperties(path);
            // Set props = keyvaluemap.entrySet(); //configuration.entrySet();
            // render(props);
            final List liste = file;
            render(liste);
        }
    }

    static void readPlayperties(final String path) {
        try {
            // Open the file that is the first
            // command line parameter
            final FileInputStream fstream = new FileInputStream(path);
            // Get the object of DataInputStream
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Line tmpLine;
            int id = 0;
            file.clear();
            keylinemap.clear();
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                tmpLine = new Line(strLine);
                if ((file.size() > 0) && strLine.contains("~~~~~")) {
                    file.get(file.size() - 1).setTitle(true);
                    file.get(file.size() - 1).setId(id++);
                    if (file.get(file.size() - 1).getId() > 0) {
                        file.get(file.size() - 2).setBlockEnd(true);
                    }
                }
                file.add(tmpLine);
                // TODO recup key and set line
                // keylinemap.put(key, value);
            }
            // Close the input stream
            in.close();
        }
        catch (final Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void routes() {
        preparePage(APP_ROUTES);
    }

    public static void save() {

        Play.stop();
        System.out.println("~ Application Stopped");

        System.out.println("~ Saving configuration ...");
        // backup old file
        final File old = new File(APP_CONF_BAK);
        old.delete();

        fileperties = new ArrayList<Object>();
        controlPlayperties();

        final File cur = new File(APP_CONF);
        cur.renameTo(old);

        writePlayperties();
        System.out.println("~ Configuration Saved");

        Play.start();
        System.out.println("~ Application Restarted");
        index();
    }

    static void writePlayperties() {
        try {
            // Open an output stream
            final File f = new File(APP_CONF);

            final FileOutputStream fout = new FileOutputStream(f);
            fout.flush();

            // Print a line of text
            final PrintStream p = new PrintStream(fout);
            p.flush();

            for (final Object o : fileperties) {
                p.println(o);
            }

            p.close();
            // Close our output stream
            fout.close();

        }
        catch (final Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

}
