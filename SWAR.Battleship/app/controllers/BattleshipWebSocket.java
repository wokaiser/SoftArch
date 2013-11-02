package controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import model.general.Constances;
import model.playground.Coordinates;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controller.GameController;
import database.GameContent;
import play.libs.Json;
import play.libs.F.Callback;
import play.mvc.*;
import view.game.Game;


public class BattleshipWebSocket extends Controller {
	
	/* this Map holds all controllers, for all players. A play can get his controller with his uuid */
	private static HashMap<String, OnlineGame> onlineGames = new HashMap<String, OnlineGame>();
	/* this list holds all open multiplayer games, which mean that one player wait for another player */
	private static List<String> openGames = new LinkedList<String>();
	
	/**
	 * Generate a unique id to store a own GameController for each player in a Hash Map.
	 * @return A unique id
	 */
	public static String getUuid() {
		String uuid = UUID.randomUUID().toString();
		while (onlineGames.containsKey(uuid)) {
			uuid = UUID.randomUUID().toString();
		}
		onlineGames.put(uuid, null);
		return uuid;
	}
	
	/**
	 * Get the controller by uuid. If the controller is not initialised a new controller will be created.
	 * @return The GameController
	 */
	public static OnlineGame getOnlineGame(String uuid) {
		if ((!onlineGames.containsKey(uuid))
		  ||(null == onlineGames.get(uuid))){
			OnlineGame controller = new OnlineGame(GameController.HUMAN_PLAYER_1, Game.newGameController());
			onlineGames.put(uuid, controller);
			return controller;
		}
		return onlineGames.get(uuid);
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
                OnlineGame onlineGame = getOnlineGame(uuid);
                GameController controller = onlineGame.getController();
            	/* we have to remove all possible Observers and set them again, because old references may exist */
                controller.removeAllObservers();
                controller.addObserver(new GameWithWui(onlineGame, in, out));
            	
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
    
    public void onMessage(final OnlineGame onlineGame, final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) {
    	final GameController controller = onlineGame.getController();
		// For each event received on the socket,
	    in.onMessage(new Callback<JsonNode>() {
	        public void invoke(JsonNode event) {
	        	ObjectNode status = Json.newObject();
	        	                    
	        	/* new single player game */
	        	if (null != event.findPath("newSinglePlayerGame").textValue()) {
	        		onlineGame.setPlayer(GameController.HUMAN_PLAYER_1);
	        		controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, GameController.HUMAN_PLAYER_1, GameController.AI_PLAYER_1, GameContent.SINGLEPLAYER);
	            	status.put("ownPlayground", controller.getOwnPlaygroundAsJson());
	            	status.put("enemyPlayground", controller.getEnemyPlaygroundAsJson());
	      			out.write(status);
	      			return;
	        	}
	        	
	        	/* new multi player game */
	        	if (null != event.findPath("newMultiPlayerGame").textValue()) {
	        		System.out.println("newMultiPlayerGame");
	        		return;
	        	}
	        	
	        	if (controller.gameFinished()) {
	    			status.put("info", "Creating a new game is required.");
	    			out.write(status);
	    			return;
	        	}
	        	
	        	if ((event.findPath("shootX").canConvertToInt())
	        	  &&(event.findPath("shootY").canConvertToInt())) {
	        		int x = event.findPath("shootX").asInt();
	        		int y = event.findPath("shootY").asInt();
	        		Coordinates target = new Coordinates(controller.getRows(), controller.getColumns());
	        		target.setRow(x);
	        		target.setColumn(y);
	        		controller.shoot(onlineGame.getPlayer(), target);
	        		return;
	        	}
	        	
				status.put("error", "Illegal call to websocket.");
				out.write(status);
				return;
	        	
	        }
	    });
    }
}