package controllers;

import model.general.Constances;
import model.playground.Coordinates;
import play.libs.Json;
import play.libs.F.Callback;
import play.mvc.WebSocket;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.GameController;
import database.GameContent;
import interfaces.IObserver;

public class GameWithWui implements IObserver{
	private final WebSocket.Out<JsonNode> out;
	private final GameController controller;
	private String player;
	
	public GameWithWui(final GameController controller, final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) {
		this.controller = controller;
		this.out = out;
	
    	// For each event received on the socket,
        in.onMessage(new Callback<JsonNode>() {
            public void invoke(JsonNode event) {
            	ObjectNode status = Json.newObject();
            	                    
            	/* new single player game */
            	if (null != event.findPath("newSinglePlayerGame").textValue()) {
            		player = GameController.HUMAN_PLAYER_1;
            		controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, player, GameController.AI_PLAYER_1, GameContent.SINGLEPLAYER);
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
            		controller.shoot(player, target);
            		return;
            	}
            	
    			status.put("error", "Illegal call to websocket.");
    			out.write(status);
    			return;
            	
            }
        });
	}
	
    @Override
    public void updateOnLoaded() {

    }
	
	@Override
	public void update() {
		ObjectNode status = Json.newObject();                            	
   
    	if (controller.switchedPlayer() && controller.getGameType() == GameContent.MULTIPLAYER) {
    		System.out.println(controller.getActivePlayer()+" please select your target");	
		}
		model.general.Status controllerStatus = controller.getStatus();

		if (controllerStatus.errorExist()) {
			status.put("error", controllerStatus.getError());
  			out.write(status);
  			controller.getStatus().clear();
  			return;
		}
		if (controllerStatus.textExist()) {
			status.put("info", controllerStatus.getText());
  			out.write(status);
  			controller.getStatus().clear();
  			status.removeAll();
		}
		
		if (controller.gameFinished()) {
			status.put("info", controllerStatus.getText());
  			out.write(status);
  			controller.getStatus().clear();
  			status.removeAll();
  			return;
		}

		/* send an update */
    	status.put("ownPlayground", controller.getOwnPlaygroundAsJson());
    	status.put("enemyPlayground", controller.getEnemyPlaygroundAsJson());
		out.write(status);
		return;
	}

}
