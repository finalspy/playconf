package play.modules.playconf;

import play.Logger;
import play.PlayPlugin;
import play.mvc.Router;

public class PlayConfPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        Logger.debug("PlayConfPlugin.onApplicationStart()");
        super.onApplicationStart();
    }

    @Override
    public void onRoutesLoaded() {
        Logger.debug("PlayConfPlugin.onRoutesLoaded()");
        Router.addRoute("GET", "/@playconf", "playconf.PlayConf.index");
        Router.addRoute("GET", "/@playconfsave", "playconf.PlayConf.save");
    }

    @Override
    public void onApplicationReady() {
        System.out.println("~");
        System.out.println("~ si ça marche je paye une biere a nicogiard");
        System.out.println("~");
    }

}
