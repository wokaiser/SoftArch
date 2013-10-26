package controllers;

import interfaces.IObserver;
import java.util.HashMap;
import java.util.UUID;
import model.general.Constances;
import model.playground.Coordinates;
import modules.SettingsModule;
import com.fasterxml.jackson.databind.JsonNode;
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
				SettingsModule settings = new SettingsModule();
				settings.setSettings(SettingsModule.Settings.Easy);
				Injector inject = Guice.createInjector(settings);
				GameController controller = inject.getInstance(GameController.class);
				controllers.put(uuid, controller);
				break;
			}
		}
		return uuid;
	}
	
	public static boolean validController(String uuid) {
		return controllers.containsKey(uuid) ? true : false;
	}
	
	
	public static Result load() {
		System.out.println("Here I am!");
		JsonNode json = request().body().asJson();
		ObjectNode result = Json.newObject();
		if(json == null) {
			return badRequest("Expecting Json data");
		} else {
			String name = json.findPath("name").textValue();
			
			if(name == null) {
				return badRequest("Missing parameter [name]");
			} else {
				System.out.println("Hallo " + name);
				result.put("status", "OK");
				result.put("message", "Hello" + name);
				return ok(result);
			}
		}
	}  
	

    public static WebSocket<JsonNode> connect() {
        final String uuid = session("uuid");
    	
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
            			controller.getStatus().clear();
               
                		if (controller.switchedPlayer() && controller.getGameType() == GameController.MULTIPLAYER) {
                			System.out.println(controller.getActivePlayer()+" please select your target");	
                		}
                		model.general.Status controllerStatus = controller.getStatus();

                		if (controllerStatus.errorExist()) {
                			status.put("error", controllerStatus.getError());
                  			out.write(status);
                			return;
                		}
                		if (controllerStatus.textExist()) {
                			status.put("info", controllerStatus.getText());
                  			out.write(status);
                			return;
                		}
                   		                        		                       		
                		if (controller.gameFinished()) {
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
        				System.out.println(controllers.get(uuid).getOwnPlaygroundAsString());
                    	System.out.println(controllers.get(uuid).getEnemyPlaygroundAsString());
            		}
            	}
            	
            	
            	// For each event received on the socket,
                in.onMessage(new Callback<JsonNode>() {
                    public void invoke(JsonNode event) {
                    	ObjectNode status = Json.newObject();
                    	
                    	GameController controller = controllers.get(uuid);
                    	                    	
                    	if (null != event.findPath("newSinglePlayerGame").textValue()) {
                    		System.out.println("newSinglePlayerGame");
                    		controller.initController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, "Human", GameController.AI_PLAYER_1, GameController.SINGLEPLAYER);
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
                    	}
                    	
                    }
                });
                
                GameController controller = controllers.get(uuid);
                
                // on load of websocket.
                ObjectNode status = Json.newObject();
            	
            	if (controller.gameFinished()) {
        			status.put("info", "Creating a new game is required.");
        			out.write(status);
        			return;
            	}
            	            	
            	// we have to set the Observers again, because a possible old reference to the
            	// Websocket exist.
    			controller.removeAllObservers();
        		controller.addObserver(new Playground());
            	

            	
            	status.put("info", "A game is active..");
    			out.write(status);
            }
        };
    }
}