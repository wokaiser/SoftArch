package controllers;

import java.util.HashMap;
import java.util.UUID;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.GameController;
import play.libs.Json;
import play.mvc.*;
import view.game.Game;


public class BattleshipWebSocket extends Controller {
	
	/* this Map holds all controllers, for all players. A play can get his controller with his uuid */
	private static HashMap<String, GameController> controllers = new HashMap<String, GameController>();
	
	/**
	 * Generate a unique id to store a own GameController for each player in a Hash Map.
	 * @return A unique id
	 */
	public static String getUuid() {
		String uuid = UUID.randomUUID().toString();
		while (controllers.containsKey(uuid)) {
			uuid = UUID.randomUUID().toString();
		}
		controllers.put(uuid, null);
		return uuid;
	}
	
	/**
	 * Get the controller by uuid. If the controller is not initialised a new controller will be created.
	 * @return The GameController
	 */
	public static GameController getController(String uuid) {
		if ((!controllers.containsKey(uuid))
		  ||(null == controllers.get(uuid))){
			GameController controller = Game.newGameController();
			controllers.put(uuid, controller);
			return controller;
		}
		return controllers.get(uuid);
	}

	/**
	 * The WebSocket, to which the client connects
	 * @return A handle for the Websocket.
	 */
    public static WebSocket<JsonNode> connect() {
    	/* uuid has to be loaded from here, because in next statement is no http context available */
    	final String uuid = session("uuid");
    	
        return new WebSocket<JsonNode>() {
            /* Called when the Websocket Handshake is done. */
            public void onReady(final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out){    
                ObjectNode status = Json.newObject();
                /* check if a uuid is in the session scope. If not cookies are probably disabled */
                if (null == uuid) {
        			status.put("error", "Enable Cookies and refresh the site to play battleships.");
        			out.write(status);
                	return;
                }
                /* get the controller with the uuid */
                GameController controller = getController(uuid);
            	/* we have to remove all possible Observers and set them again, because old references may exist */
    			controller.removeAllObservers();
        		controller.addObserver(new GameWithWui(controller, in, out));
            	
            	if (controller.gameFinished()) {
        			status.put("info", "Welcome to Battleship. Create a new Game to get started.");
        			out.write(status);
        			return;
            	}
               	/* write the playgroud to the client */
            	status.put("ownPlayground", controller.getOwnPlaygroundAsJson());
            	status.put("enemyPlayground", controller.getEnemyPlaygroundAsJson());
      			out.write(status);
            }
        };
    }
}