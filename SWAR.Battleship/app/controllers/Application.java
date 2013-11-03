package controllers;

import java.net.Inet4Address;
import java.net.UnknownHostException;

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
        
        try {
        	ip = Inet4Address.getLocalHost().getHostAddress();
        	ip = "localhost";	//TODO remove
        } catch (UnknownHostException e) {
			ip = "NOT_AVAILABLE";
			e.printStackTrace();
		}

    	return ok(app.render(ip));
    }
}
