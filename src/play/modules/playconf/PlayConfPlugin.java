package play.modules.playconf;

import play.Logger;
import play.PlayPlugin;
import play.mvc.Router;

public class PlayConfPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        System.out.println("PlayConfPlugin.onApplicationStart()");
        super.onApplicationStart();
    }

    @Override
    public void onRoutesLoaded() {
        System.out.println("PlayConfPlugin.onRoutesLoaded()");
        Router.addRoute("GET", "/@playconf", "playconf.PlayConf.index");
        Router.addRoute("GET", "/@playconf/messages", "playconf.PlayConf.messages");
        Router.addRoute("GET", "/@playconf/routes", "playconf.PlayConf.routes");
        Router.addRoute("GET", "/@playconfsave", "playconf.PlayConf.save");
    }

    @Override
    public void onApplicationReady() {
        System.out.println("~ Go to /@playconf to edit your play application configuration.");
    }

}
