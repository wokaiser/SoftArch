package view.game;

import interfaces.IObserver;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.*;

import view.gui.PlaygroundFrame;
import view.gui.StatusPanel;
import model.general.Constances;
import controller.GameController;
import database.GameContent;

/** 
 * The GameWithGui class supports playing Battleships over a graphical user interface.
 * @author Dennis Parlak
 */
public class GameWithGui implements IObserver {
	
	private static final int DEFAULT_PANEL_WIDTH = 1200;
	private static final int DEFAULT_PANEL_HEIGHT = 710;
	private static final int KEYEVENT_NONE = -1;
	
	private GameController controller;
	private StatusPanel statusPanel;
	private PlaygroundFrame ownFrame;
	private PlaygroundFrame enemyFrame;
	private JPanel mainPanel;
	private JPanel playgroundsPanel;
	private JFrame mainFrame;
	private JMenuItem save;

	/**
	 * Creates a new GameWithGui Object.
	 */
	public GameWithGui(final GameController controller) {
		this.controller = controller;
		mainFrame = new JFrame();
        this.initMenuBar();

		JPanel ausgabe = new JPanel();
		ausgabe.setLayout(new BorderLayout(1, 1));
		ausgabe.setBorder(BorderFactory.createTitledBorder("Output"));
   	

		mainPanel = new JPanel(new BorderLayout());
		statusPanel = new StatusPanel();
		playgroundsPanel = new JPanel(new GridLayout(1, 2));
		
		mainPanel.add(playgroundsPanel, BorderLayout.PAGE_START);
		mainPanel.add(statusPanel, BorderLayout.PAGE_END);
		
		initMainFrame();
	}
	
	/**
	 * Creates a new game
	 * @param The name of player1 (To use AI set to GameController.AI_PLAYER_1)
	 * @param The name of player2 (To use AI set to GameController.AI_PLAYER_2)
	 */
	private void newGame(String player1, String player2, int gameType) {
		playgroundsPanel.removeAll();
		controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, player1, player2, gameType);
		ownFrame = new PlaygroundFrame(controller, controller.getActivePlayer()); 
		controller.switchPlayer();
		enemyFrame = new PlaygroundFrame(controller, controller.getActivePlayer()); 
		controller.switchPlayer();
		playgroundsPanel.add(ownFrame.get());
		playgroundsPanel.add(enemyFrame.get());
		save.setEnabled(true);
		initMainFrame();
	}
	
	/**
	 * Initialize the main frame (size, visibility,..)
	 */
	private void initMainFrame() {
		mainFrame.setContentPane(mainPanel);
		mainFrame.setTitle("Battleships");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);    
		mainFrame.setSize(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
		statusPanel.update(controller.getStatus());
	}
	
	/**
	 * Creates a new JMenuItem with the specific paramters
	 * @param text The text of the JMenuItem
	 * @param keyEvent The KeyEvent combined with Ctrl
	 * @param listener The listener of this JMenuItem
	 * @return A new JMenuItem
	 */
	private JMenuItem createNewItem(String text, int keyEvent, ActionListener listener) {
		JMenuItem result = new JMenuItem(text);
		if(keyEvent != KEYEVENT_NONE) {
			result.setMnemonic(keyEvent);
		}
		result.addActionListener(listener);		
		return result;
	}
	
	/**
	 * Adds stored games from the database to the Load Menu
	 * @param loadMenu The JMenu the saved games should be attached to
	 */
	private void addStoredGamesToLoadMenu(JMenu loadMenu) {
		List<String> games = controller.getStoredGames();
		for (String name : games) {
			final String tmp = name;
			JMenuItem item = createNewItem(tmp, KEYEVENT_NONE, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.loadGame(tmp);
					update();
				}
			});
			loadMenu.add(item);
		}		
	}
	
	/**
	 * Initialize the menu bar (NewGame menu item, Quit menu item,...)
	 */
	private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
		JMenuItem quit;
		JMenuItem single;
		JMenuItem multi;
		
		JMenu menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		
		JMenu loadMenu = new JMenu("Load game");
		loadMenu.setMnemonic(KeyEvent.VK_L);
		
		save = createNewItem("Save game", KeyEvent.VK_S, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(true) {
					String name = JOptionPane.showInputDialog("Enter savegame name!");
					if(name == null || name.isEmpty()) {
						break;
					}
					if (!controller.saveGame(name)) {
						JOptionPane.showMessageDialog(mainFrame, "Savegame already taken, choose another one!");
						continue;
					}
					break;
				}
			}
		});
		save.setEnabled(false);
		
		single = createNewItem("New Single Player Game", KeyEvent.VK_N, new ActionListener() {
			/**
			 * The action listener to create a new single player game
			 * @param event The occurred event
			 */
			public void actionPerformed(ActionEvent event) {
				newGame("Spieler 1", GameController.AI_PLAYER_1, GameContent.SINGLEPLAYER);
			}
		});
		
		multi = createNewItem("New Multi Player Game", KeyEvent.VK_M, new ActionListener() {
			/**
			 * The action listener to create a new mulit player game
			 * @param event The occurred event
			 */
			public void actionPerformed(ActionEvent event) {
				newGame("Spieler 1", "Spieler 2", GameContent.MULTIPLAYER);
			}
		});
		
		quit = createNewItem("Quit", KeyEvent.VK_Q, new ActionListener() {
			/**
			 * The action listener to exit the game
			 * @param event The occurred event
			 */
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		addStoredGamesToLoadMenu(loadMenu);
		
		menu.add(single);
		menu.add(multi);
		menu.add(loadMenu);
		menu.add(save);
		menu.add(quit);
		menuBar.add(menu);
		mainFrame.setJMenuBar(menuBar);
	}

	/**
	 * Implementation of update method to be observable.
	 */
	@Override
	public void update() {
		playgroundsPanel.removeAll();
		playgroundsPanel.repaint();
		if (controller.switchedPlayer() && controller.getGameType() == GameContent.MULTIPLAYER) {
			JOptionPane.showMessageDialog(mainPanel, controller.getActivePlayer()+" please select your target", "Switched player", JOptionPane.INFORMATION_MESSAGE);	
		}
		statusPanel.update(controller.getStatus());
		controller.getStatus().clear();
		
		if (controller.gameFinished()) {
			String winner = controller.getStatus().getText();
			controller.getStatus().clear();
			controller.getStatus().addText("Select a new Game in the Menu.");
			statusPanel.update(controller.getStatus());
			JOptionPane.showMessageDialog(mainFrame, winner, "Game Finished", JOptionPane.INFORMATION_MESSAGE);
			playgroundsPanel.repaint();
			return;
		}
				
		while (controller.isAI()) {
			if (controller.gameFinished()) {
				playgroundsPanel.removeAll();
				statusPanel.update(controller.getStatus());
				return;
			}
			int status = controller.shoot(null);
			ownFrame.update();
			enemyFrame.update();
			if (Constances.SHOOT_HIT != status && Constances.SHOOT_DESTROYED != status) {
				break;
			}
		}
		
		if (controller.getActivePlayer() == ownFrame.getPlayer()) {
			playgroundsPanel.add(ownFrame.get());
			playgroundsPanel.add(enemyFrame.get());	
		}
		else {
			playgroundsPanel.add(enemyFrame.get());	
			playgroundsPanel.add(ownFrame.get());
		}

		playgroundsPanel.repaint();
	}
}

