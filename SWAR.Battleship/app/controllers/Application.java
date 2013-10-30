package controllers;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {
    
    public static Result index() {
    	String uuid = session("uuid");
    	String ip = null;

        if ((null == uuid)
          ||(!Game.validController(uuid))) {
        	uuid = Game.newGame();
        	session("uuid", uuid);
        }    	
        
        try {
        	System.out.println(InetAddress.getLocalHost().getHostAddress());
        	ip = Inet4Address.getLocalHost().getHostAddress();
        	ip = "localhost";	//TODO remove
        } catch (UnknownHostException e) {
			ip = "NOT_AVAILABLE";
			e.printStackTrace();
		}
    	return ok(app.render(ip));
    }
}
