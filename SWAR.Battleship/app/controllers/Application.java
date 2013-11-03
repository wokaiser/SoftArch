package controllers;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
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
        	ip = getActiveNetConnectionLocalHostIP();
        } catch (UnknownHostException e) {
			ip = "NOT_AVAILABLE";
			e.printStackTrace();
		}

    	return ok(app.render(ip));
    }
    
    /**
     * Gets the IP Adress of the local host regarding the active Internet adapter
     * @return The IP of the local host
     */
    private static String getActiveNetConnectionLocalHostIP() {
    	Socket s;
    	String result = "localhost";
		try {
			s = new Socket("google.com", 80);
	    	result = s.getLocalAddress().getHostAddress();
	    	s.close();
		} catch (IOException e) {
			//TODO
		}
		return result;
    }
}
