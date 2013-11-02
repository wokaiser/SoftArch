package controllers;

import controller.GameController;

public class OnlineGame {
	private String player;
	private GameController controller;
	
	public OnlineGame(String player, GameController controller) {
		this.setPlayer(player);
		this.controller = controller;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public GameController getController() {
		return controller;
	}

	public void setController(GameController controller) {
		this.controller = controller;
	}
}
