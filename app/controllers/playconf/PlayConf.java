package controllers.playconf;

import play.Play;
import play.mvc.Controller;

import java.io.File;
import java.util.Set;


public class PlayConf extends Controller {
    public static boolean confReadableGlobal = false;
    public static String separator = System.getProperty("file.separator");

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
            Set props = Play.configuration.entrySet();

            render(props);
        }
    }


    public static void save() {
        System.out.println("SAVE...");
        index();
    }
//        /**
//     * Read application.conf and resolve overriden key using the play id mechanism.
//     */
//    @SuppressWarnings("unchecked")
//    static void readConfiguration() {
//        VirtualFile appRoot = VirtualFile.open(applicationPath);
//        conf = appRoot.child("conf/application.conf");
//        try {
//            configuration = IO.readUtf8Properties(conf.inputstream());
//        } catch (RuntimeException e) {
//            if (e.getCause() instanceof IOException) {
//                Logger.fatal("Cannot read application.conf");
//                System.exit(0);
//            }
//        }
//        // Ok, check for instance specifics configuration
//        Properties newConfiguration = new Properties();
//        Pattern pattern = Pattern.compile("^%([a-zA-Z0-9_\\-]+)\\.(.*)$");
//        for (Object key : configuration.keySet()) {
//            Matcher matcher = pattern.matcher(key + "");
//            if (!matcher.matches()) {
//                newConfiguration.put(key, configuration.get(key).toString().trim());
//            }
//        }
//        for (Object key : configuration.keySet()) {
//            Matcher matcher = pattern.matcher(key + "");
//            if (matcher.matches()) {
//                String instance = matcher.group(1);
//                if (instance.equals(id)) {
//                    newConfiguration.put(matcher.group(2), configuration.get(key).toString().trim());
//                }
//            }
//        }
//        configuration = newConfiguration;
//        // Resolve ${..}
//        pattern = Pattern.compile("\\$\\{([^}]+)}");
//        for (Object key : configuration.keySet()) {
//            String value = configuration.getProperty(key.toString());
//            Matcher matcher = pattern.matcher(value);
//            StringBuffer newValue = new StringBuffer(100);
//            while (matcher.find()) {
//                String jp = matcher.group(1);
//                String r;
//                if (jp.equals("application.path")) {
//                    r = Play.applicationPath.getAbsolutePath();
//                } else if (jp.equals("play.path")) {
//                    r = Play.frameworkPath.getAbsolutePath();
//                } else {
//                    r = System.getProperty(jp);
//                    if (r == null) {
//                        Logger.warn("Cannot replace %s in configuration (%s=%s)", jp, key, value);
//                        continue;
//                    }
//                }
//                matcher.appendReplacement(newValue, r.replaceAll("\\\\", "\\\\\\\\"));
//            }
//            matcher.appendTail(newValue);
//            configuration.setProperty(key.toString(), newValue.toString());
//        }
//        // Include
//        Map toInclude = new HashMap(16);
//        for (Object key : configuration.keySet()) {
//            if (key.toString().startsWith("@include.")) {
//                try {
//                    toInclude.putAll(IO.readUtf8Properties(appRoot.child("conf/" + configuration.getProperty(key.toString())).inputstream()));
//                } catch (Exception ex) {
//                    Logger.warn("Missing include: %s", key);
//                }
//            }
//        }
//        configuration.putAll(toInclude);
//        // Plugins
//        for (PlayPlugin plugin : plugins) {
//            plugin.onConfigurationRead();
//        }
//    }
}
