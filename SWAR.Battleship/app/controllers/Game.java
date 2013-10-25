package controllers;

import play.*;
import play.mvc.*;
import play.libs.Json;
import play.data.DynamicForm;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode; 
import views.html.*;
import play.mvc.Http;

public class Game extends Controller {
    
    public static Result load() {
        DynamicForm data = form().bindFromRequest();
        ObjectNode status = Json.newObject();;

        status.put("bsp", "ret");
        
        System.out.println(data.get("name"));
        
        return ok(status);
    }
}
