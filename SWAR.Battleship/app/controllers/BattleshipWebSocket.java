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
import controller.GameContent;
import play.libs.Json;
import play.libs.F.Callback;
import play.mvc.*;
import util.Connection;
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
	 * Get the OnlineGame by uuid. If the controller of the OnlineGame is not initialized a new controller will be created.
	 * @return The GameController
	 */
	public static OnlineGame getOnlineGame(String uuid) {
		System.out.println(uuid);
		if (!onlineGames.containsKey(uuid)) {
			onlineGames.put(uuid, new OnlineGame(null, null));
		}
		if (null == onlineGames.get(uuid)) {
			onlineGames.put(uuid, new OnlineGame(null, null));	
		}
		return onlineGames.get(uuid);
	}
	/**
	 * Get the a new OnlineGame by uuid. If a controller already exist, it will be removed an replaced with a new one.
	 * This will be done, because a second player also can own a reference to this object and so we avoid that the
	 * second player see the same game.
	 * @return The GameController
	 */
	public static OnlineGame getNewOnlineGame(String uuid) {
		System.out.println("getNewOnlineGame - 1");
		onlineGames.remove(uuid);
		System.out.println("getNewOnlineGame - 2");
		OnlineGame onlineGame = new OnlineGame(GameContent.HUMAN_PLAYER_1, null);
		System.out.println("getNewOnlineGame - 3");
		onlineGame.setController(Game.newGameController());
		System.out.println("getNewOnlineGame - 4");
		onlineGames.put(uuid, onlineGame);
		
		return onlineGame;
	}
	
	public static OnlineGame joinOnlineGame(String joinUuuid, String ownUuid) {
		OnlineGame controller = new OnlineGame(GameContent.HUMAN_PLAYER_2, onlineGames.get(joinUuuid).getController());
		onlineGames.put(ownUuid, controller);
		return controller;
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
        			status.put("error", "Enable Cookies and connect to "+Connection.getActiveNetConnectionLocalHostIP()+":9000 to play battleships.");
        			out.write(status);
                	return;
                }
                /* get the controller with the uuid */
                OnlineGame onlineGame = getOnlineGame(uuid);

            	/* we have to remove all possible Observers and set them again, because old references may exist */
                onMessage(uuid, in, out);
                /* check if no controller is active */
                if (null == onlineGame.getController()) {
        			status.put("info", "Welcome to Battleship. Create a new Game to get started.");
        			out.write(status);
        			return;
                }
            	
                GameController controller = onlineGame.getController();
                /* check if a game is still active */
            	if (controller.gameFinished()) {
        			status.put("info", "Welcome to Battleship. Create a new Game to get started.");
        			out.write(status);
        			return;
            	}
                controller.removeObserver(onlineGame.getPlayer());
                controller.addObserver(onlineGame.getPlayer(), new GameWithWui(onlineGame, in, out));
               	/* write the playground to the client */
                status.put("player", onlineGame.getPlayer());
            	status.put("ownPlayground", controller.getOwnPlaygroundAsJson(onlineGame.getPlayer()));
            	status.put("enemyPlayground", controller.getEnemyPlaygroundAsJson(onlineGame.getPlayer()));
      			out.write(status);
            }
        };
    }
    
    private static void onMessage(final String uuid, final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) {
		// For each event received on the socket,
	    in.onMessage(new Callback<JsonNode>() {
	        public void invoke(JsonNode event) {
	        	OnlineGame onlineGame = getOnlineGame(uuid);
	        	GameController controller = onlineGame.getController();
	        	ObjectNode status = Json.newObject();
	        		        	                    
	        	/* new single player game */
	        	if (null != event.findPath("newSinglePlayerGame").textValue()) {
	        		/* remove the own uuid, if a game is already open with this uuid */
	        		openGames.remove(uuid);
	        		System.out.println("newSinglePlayerGame");
	        		onlineGame = getNewOnlineGame(uuid);
	        		System.out.println("1");
	        		onlineGame.getController().newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, GameContent.HUMAN_PLAYER_1, GameContent.AI_PLAYER_1_EASY, GameContent.SINGLEPLAYER);
	        		onlineGame.getController().addObserver(onlineGame.getPlayer(), new GameWithWui(onlineGame, in, out));
	        		System.out.println("2");
	        		status.put("player", onlineGame.getPlayer());
	        		status.put("ownPlayground", onlineGame.getController().getOwnPlaygroundAsJson(onlineGame.getPlayer()));
	            	status.put("enemyPlayground", onlineGame.getController().getEnemyPlaygroundAsJson(onlineGame.getPlayer()));
	            	System.out.println("3");
	      			out.write(status);
	      			System.out.println("4");
	        		onlineGame.getController().startGame();
	        		System.out.println("5");
	      			return;
	        	}
	        	
	        	/* new multiplayer game */
	        	if (null != event.findPath("newMultiPlayerGame").textValue()) {
	        		/* remove the own uuid, if a game is already open with this uuid */
	        		openGames.remove(uuid);
	        		/* check if a game is already open, than join the game */
	        		if (0 < openGames.size()) {
	        			System.out.println("JOIN MultiPlayerGame");	
		        		onlineGame = joinOnlineGame(openGames.get(0), uuid);
		        		onlineGame.getController().addObserver(onlineGame.getPlayer(), new GameWithWui(onlineGame, in, out));
		        		status.put("player", onlineGame.getPlayer());
		        		status.put("ownPlayground", onlineGame.getController().getOwnPlaygroundAsJson(onlineGame.getPlayer()));
		            	status.put("enemyPlayground", onlineGame.getController().getEnemyPlaygroundAsJson(onlineGame.getPlayer()));
		      			out.write(status);
		        		onlineGame.getController().startGame();
		      			return;
	        		} else {
		        		System.out.println("newMultiPlayerGame");
		        		openGames.add(uuid);
		        		onlineGame = getNewOnlineGame(uuid);
		        		onlineGame.getController().newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, GameContent.HUMAN_PLAYER_1, GameContent.HUMAN_PLAYER_2, GameContent.MULTIPLAYER);
		        		onlineGame.getController().addObserver(onlineGame.getPlayer(), new GameWithWui(onlineGame, in, out));
		        		status.put("player", onlineGame.getPlayer());
		        		status.put("ownPlayground", onlineGame.getController().getOwnPlaygroundAsJson(onlineGame.getPlayer()));
		            	status.put("enemyPlayground", onlineGame.getController().getEnemyPlaygroundAsJson(onlineGame.getPlayer()));
		      			out.write(status);
		      			return;
	        		}
	        	}
	       
	        	/* check if no controller is ready */
	        	if (null == controller) {
	        		status.put("info", "Create a new game to start with Battleship.");
	        		out.write(status);
	        		return;
	        	}
	        	
	        	if (controller.gameFinished()) {
	    			status.put("info", "Creating a new game is required.");
	    			out.write(status);
	    			return;
	        	}
	        	
	        	if ((event.findPath("shootX").canConvertToInt())
	        	  &&(event.findPath("shootY").canConvertToInt())) {
	        		System.out.println("shoot from "+onlineGame.getPlayer());
	        		Coordinates target = new Coordinates(event.findPath("shootX").asInt(), event.findPath("shootY").asInt());
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