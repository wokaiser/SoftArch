package controllers;

import util.Connection;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {
    
    public static Result index() {
    	String uuid = session("uuid");
    	String ip = null;

    	/* get a new uuid, if no one is actually available. */
        if (null == uuid) {
        	session("uuid", BattleshipWebSocket.getUuid());
        }
        
        ip = Connection.getActiveNetConnectionLocalHostIP();
    	return ok(app.render(ip));
    }
}
