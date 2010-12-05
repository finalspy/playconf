package controllers.playconf;

import java.io.File;

import play.Play;
import play.mvc.Controller;



public class PlayConf extends Controller {
	public static boolean confReadableGlobal = false;
	public static String separator = System.getProperty("file.separator");

	public static void index() {
		
		// Check if previous report exist
		if (!confReadableGlobal) {

			String coverageTestPath = Play.applicationPath + separator + "conf"
					+ separator + "application.conf";

			File f = new File(coverageTestPath);

			if (f.exists())
				confReadableGlobal = true;
		}

		boolean confReadable = confReadableGlobal; // use local variable because
												// global variable is not report
												// on view ?
		render(confReadable);
	}
}
