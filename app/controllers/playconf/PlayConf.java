package controllers.playconf;

import com.konkest.util.Properties;
import play.Play;
import play.mvc.Controller;
import play.vfs.VirtualFile;

import java.io.*;
import java.util.*;


public class PlayConf extends Controller {
    public static boolean confReadableGlobal = false;
    public static String separator = System.getProperty("file.separator");
    /**
     * The app configuration (already resolved from the framework id)
     */
    public static Properties configuration;
    /**
     * The main application.conf
     */
    public static VirtualFile conf;
    /**
     * The framework ID
     */
    public static String id;

    private static List<Object> fileperties = new ArrayList<Object>();
    private static Map<String, Integer> keylinemap = new HashMap<String, Integer>();
    private static Map<String, String> keyvaluemap = new HashMap<String, String>();

    public static void index() {

        // Check if previous report exist
        if (!confReadableGlobal) {

            String coverageTestPath = Play.applicationPath + separator + "conf"
                    + separator + "application.conf";

            File f = new File(coverageTestPath);

            if (f.exists()) {
                confReadableGlobal = true;
            }
        }

        if (confReadableGlobal) {
            //readConfiguration();
            readPlayperties();
            Set props = keyvaluemap.entrySet(); //configuration.entrySet();
            System.out.println("test= " + props);
            render(props);
        }
    }


    public static void save() {
        System.out.println("~ Saving configuration ...");
        System.out.println(request.args.entrySet());
        System.out.println("test = [" + params.get("conf.test") + "]");
        // backup old file
        // pour chaque key si value changée alors set
        for (Map.Entry<String, String[]> e : params.all().entrySet()) {
            System.out.println(e.getKey() + " / " + e.getValue());
        }
        // puis properties.store ...
        // ATTENTION cela ne préserve pas les commentaires
        // réécrire Properties   en traitant les lignes # 

        System.out.println("~ Configuration Saved");
        Play.stop();
        System.out.println("~ Application Stopped");
        Play.start();
        System.out.println("~ Application Restarted");
        index();
    }


    static void readPlayperties() {
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(Play.applicationPath + separator + "conf"
                    + separator + "application.conf");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String key;
            String value;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                if (strLine.length() == 0) {
                    // vide
                    fileperties.add(strLine);
                } else if (strLine.startsWith("#")) {
                    // comment
                    fileperties.add(strLine);
                } else if (strLine.contains("=")) { // || strLine.contains(":")){
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
                // Print the content on the console
                // System.out.println(strLine);
            }
            //Close the input stream
            in.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Read application.conf and resolve overriden key using the play id mechanism.
     */
/*    @SuppressWarnings("unchecked")
    static void readConfiguration() {
        VirtualFile appRoot = VirtualFile.open(Play.applicationPath);
        conf = appRoot.child("conf/application.conf");
        try {
            //configuration = (Properties) PlayConf.readUtf8Properties(conf.inputstream());


        } catch (RuntimeException e) {

            if (e.getCause() instanceof IOException) {
                Logger.fatal("Cannot read application.conf");
                System.exit(0);
            }
        } finally {
            System.out.println("~ avant ");
            System.out.println("conf = " + configuration);
            System.out.println("~ apres ");
        }
        // Ok, check for instance specifics configuration
        Properties newConfiguration = new Properties();
        Pattern pattern = Pattern.compile("^%([a-zA-Z0-9_\\-]+)\\.(.*)$");
        for (Object key : configuration.keySet()) {
            Matcher matcher = pattern.matcher(key + "");
            if (!matcher.matches()) {
                newConfiguration.put(key, configuration.get(key).toString().trim());
            }
        }
        for (Object key : configuration.keySet()) {
            Matcher matcher = pattern.matcher(key + "");
            if (matcher.matches()) {
                String instance = matcher.group(1);
                if (instance.equals(id)) {
                    newConfiguration.put(matcher.group(2), configuration.get(key).toString().trim());
                }
            }
        }
        configuration = newConfiguration;
        // Resolve ${..}
        pattern = Pattern.compile("\\$\\{([^}]+)}");
        for (Object key : configuration.keySet()) {
            String value = configuration.getProperty(key.toString());
            Matcher matcher = pattern.matcher(value);
            StringBuffer newValue = new StringBuffer(100);
            while (matcher.find()) {
                String jp = matcher.group(1);
                String r;
                if (jp.equals("application.path")) {
                    r = Play.applicationPath.getAbsolutePath();
                } else if (jp.equals("play.path")) {
                    r = Play.frameworkPath.getAbsolutePath();
                } else {
                    r = System.getProperty(jp);
                    if (r == null) {
                        Logger.warn("Cannot replace %s in configuration (%s=%s)", jp, key, value);
                        continue;
                    }
                }
                matcher.appendReplacement(newValue, r.replaceAll("\\\\", "\\\\\\\\"));
            }
            matcher.appendTail(newValue);
            configuration.setProperty(key.toString(), newValue.toString());
        }
        // Include
        Map toInclude = new HashMap(16);
        for (Object key : configuration.keySet()) {
            if (key.toString().startsWith("@include.")) {
                try {
                    toInclude.putAll(IO.readUtf8Properties(appRoot.child("conf/" + configuration.getProperty(key.toString())).inputstream()));
                } catch (Exception ex) {
                    Logger.warn("Missing include: %s", key);
                }
            }
        }
        configuration.putAll(toInclude);
//        // Plugins
//        for (PlayPlugin plugin : plugins) {
//            plugin.onConfigurationRead();
//        }
    }*/

    /**
     * Read a properties file with the utf-8 encoding
     * @param is Stream to properties file
     * @return The Properties object
     */
    /*   private static Properties readUtf8Properties(InputStream is) {
        Properties properties = new Properties();
        try {
            properties.load(is);
            for (Object key : properties.keySet()) {
                String value = properties.getProperty(key.toString());
                String goodValue = new String(value.getBytes("iso8859-1"), "utf-8");
                properties.setProperty(key.toString(), goodValue);
            }
            is.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }*/

}
