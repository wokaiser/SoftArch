package controllers;

import play.libs.Json;
import play.mvc.WebSocket;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.GameController;
import controller.GameContent;
import interfaces.IObserver;
import interfaces.IStatus;

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
		ObjectNode playground = Json.newObject();
   
    	if (controller.switchedPlayer() && controller.getGameType() == GameContent.MULTIPLAYER) {
    		System.out.println(controller.getActivePlayer()+" please select your target");	
		}
		IStatus controllerStatus = controller.getStatus();

		if (controller.gameFinished()) {
			status.put("info", controllerStatus.getText());
  			out.write(status);
  			return;
		}
		
		if (controllerStatus.errorExist()) {
			status.put("error", controllerStatus.getError());
  			out.write(status);
  			return;
		}
		if (controllerStatus.textExist()) {
			status.put("info", controllerStatus.getText());
  			out.write(status);
		}

		/* send an update of the playground */
    	playground.put("ownPlayground", controller.getOwnPlaygroundAsJson(player));
    	playground.put("enemyPlayground", controller.getEnemyPlaygroundAsJson(player));
		out.write(playground);
		return;
	}
}
