package controllers;

import play.libs.Json;
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
	
	public GameWithWui(final OnlineGame onlineGame, final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) {
		this.controller = onlineGame.getController();
		this.player = onlineGame.getPlayer();
		this.out = out;
	}
	
	@Override
	public void update() {
		System.out.println("Update for player: "+player);
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
