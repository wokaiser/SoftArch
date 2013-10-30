package controllers;

import interfaces.IObserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import model.general.Constances;
import model.playground.Coordinates;
import database.GameContent;
import modules.DatabaseModule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controller.GameController;

import com.google.inject.Guice;
import com.google.inject.Injector;
import play.libs.F.Callback;
import play.libs.Json;
import play.mvc.*;


public class Game extends Controller {
	
	private static HashMap<String, GameController> controllers = new HashMap<String, GameController>();
	
	public static String newGame() {
		String uuid = null;
		
		/* create  a uuid, which not already exist*/
		while (true) {
			uuid = UUID.randomUUID().toString();
			if (!controllers.containsKey(uuid)) {
				Injector inject = Guice.createInjector(new DatabaseModule());
				GameController controller = inject.getInstance(GameController.class);
				controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, "Human", GameController.AI_PLAYER_1, GameContent.SINGLEPLAYER);
				controllers.put(uuid, controller);
				break;
			}
		}
		return uuid;
	}
	
	public static boolean validController(String uuid) {
		return controllers.containsKey(uuid) ? true : false;
	}

    public static WebSocket<JsonNode> connect() {
        final String uuid = session("uuid");
        System.out.println("Websocket");
        System.out.println(uuid);
        
        return new WebSocket<JsonNode>() {
            
            // Called when the Websocket Handshake is done.
            public void onReady(final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out){
                            	
            	// Nested class for the Observer of the GameController. Should be a nested class,
            	// because access to the WebSocket.Out variable is required.
            	class Playground implements IObserver {
            		@Override
            		public void update() {
            			System.out.println("---------Update reached--------");
            			ObjectNode status = Json.newObject();                            	
                    	GameController controller = controllers.get(uuid);
               
                		if (controller.switchedPlayer() && controller.getGameType() == GameContent.MULTIPLAYER) {
                			System.out.println(controller.getActivePlayer()+" please select your target");	
                		}
                		model.general.Status controllerStatus = controller.getStatus();

                		if (controllerStatus.errorExist()) {
                			System.out.println("Error");
                			status.put("error", controllerStatus.getError());
                  			out.write(status);
                  			controller.getStatus().clear();
                  			return;
                		}
                		if (controllerStatus.textExist()) {
                			System.out.println("Info");
                			status.put("info", controllerStatus.getText());
                  			out.write(status);
                  			controller.getStatus().clear();
                  			status.removeAll();
                		}
            			
                		if (controller.gameFinished()) {
                			System.out.println("Info");
                			status.put("info", controllerStatus.getText());
                  			out.write(status);
                  			controller.getStatus().clear();
                  			status.removeAll();
                  			return;
                		}
                				
                		if (controller.isAI()) {
                			while (controller.isAI()) {
                				if (controller.gameFinished()) {
                					controllerStatus.getText();
                					return;
                				}
                				int coord = controller.shoot(null);
                				if (Constances.SHOOT_HIT != coord && Constances.SHOOT_DESTROYED != coord) {
                					break;
                				}
                			}
                		}
        				/* send an update */
                    	status.put("ownPlayground", controller.getOwnPlaygroundAsJson());
                    	status.put("enemyPlayground", controller.getEnemyPlaygroundAsJson());
              			out.write(status);
              			return;
            		}
            	}
            	
            	
            	// For each event received on the socket,
                in.onMessage(new Callback<JsonNode>() {
                    public void invoke(JsonNode event) {
                    	ObjectNode status = Json.newObject();
                    	
                    	GameController controller = controllers.get(uuid);
                    	                    	
                    	if (null != event.findPath("newSinglePlayerGame").textValue()) {
                    		System.out.println("newSinglePlayerGame");
                    		controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, "Human", GameController.AI_PLAYER_1, GameContent.SINGLEPLAYER);
                    		/* send an update */
                        	status.put("ownPlayground", controller.getOwnPlaygroundAsJson());
                        	status.put("enemyPlayground", controller.getEnemyPlaygroundAsJson());
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
                    		System.out.println("Shot to target");
                    		int x = event.findPath("shootX").asInt();
                    		int y = event.findPath("shootY").asInt();
                    		Coordinates target = new Coordinates(controller.getRows(), controller.getColumns());
                    		target.setRow(x);
                    		target.setColumn(y);
                    		controller.shoot(target);
                    		return;
                    	}
                    	
            			status.put("error", "Illegal call to websocket.");
            			out.write(status);
            			return;
                    	
                    }
                });
                
                // on load of websocket.
                ObjectNode status = Json.newObject();
                
                if (null == uuid) {
        			status.put("error", "Enable Cookies and refresh the site to play battleships.");
        			out.write(status);
                	return;
                }
                
                GameController controller = controllers.get(uuid);
            	
            	if (controller.gameFinished()) {
        			status.put("info", "Creating a new game is required.");
        			out.write(status);
        			return;
            	}
            	            	
            	// we have to set the Observers again, because a possible old reference to the
            	// Websocket exist.
    			controller.removeAllObservers();
        		controller.addObserver(new Playground());
            	
        		System.out.println("A game is active");
            	status.put("ownPlayground", controller.getOwnPlaygroundAsJson());
            	status.put("enemyPlayground", controller.getEnemyPlaygroundAsJson());
      			out.write(status);
            }
        };
    }
}