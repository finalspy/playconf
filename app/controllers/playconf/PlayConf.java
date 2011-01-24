package controllers.playconf;

import com.konkest.util.Line;
import play.Play;
import play.mvc.Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private static List<Line> file = new ArrayList<Line>();

    public static final String APP_PATH = Play.applicationPath + separator + "conf" + separator;
    public static final String APP_CONF = APP_PATH + "application.conf";
    public static final String APP_MESSAGES = APP_PATH + "messages";
    public static final String APP_ROUTES = APP_PATH + "routes";
    public static final String APP_CONF_BAK = APP_CONF + ".bak";

    public static void index() {
        preparePage(APP_CONF);
    }

    public static void messages() {
        preparePage(APP_MESSAGES);
    }

    public static void routes() {
        preparePage(APP_ROUTES);
    }

    private static void preparePage(String path) {
        // Check if previous report exist
        if (confReadableGlobal |= new File(path).exists()) {
            readPlayperties(path);
            //Set props = keyvaluemap.entrySet(); //configuration.entrySet();
            //render(props);
            List liste = file;
            render(liste);
        }
    }

    public static void save() {

        Play.stop();
        System.out.println("~ Application Stopped");

        System.out.println("~ Saving configuration ...");
        // backup old file
        File old = new File(APP_CONF_BAK);
        old.delete();

        controlPlayperties();

        File cur = new File(APP_CONF);
        cur.renameTo(old);

        writePlayperties();
        System.out.println("~ Configuration Saved");

        Play.start();
        System.out.println("~ Application Restarted");
        index();
    }

    private static void controlPlayperties() {
        // pour chaque key si value changée alors set
        for (Map.Entry<String, String[]> e : params.all().entrySet()) {
            if (!e.getValue()[0].equals(keyvaluemap.get(e.getKey()))) {
                keyvaluemap.put(e.getKey(), e.getValue()[0]);
                fileperties.set(keylinemap.get(e.getKey()), e.getKey() + "=" + e.getValue()[0]);
            }
        }
    }

    static void readPlayperties(String path) {
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(path);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String key;
            String value;
            Line tmpLine;
            file.clear();
            boolean isTitle = true;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                tmpLine = new Line(strLine);
                if (file.size() > 0)
                    file.get(file.size() - 1).setTitle(strLine.contains("~~~~~"));
                file.add(tmpLine);
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
            File f = new File(APP_CONF);

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
