package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {
    
    public static Result index() {
    	String uuid = session("uuid");

        if ((null == uuid)
          ||(!Game.validController(uuid))) {
        	uuid = Game.newGame();
        	session("uuid", uuid);
        }    	
    	return ok(app.render());
    }
}
