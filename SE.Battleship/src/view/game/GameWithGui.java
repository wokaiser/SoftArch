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
        initMenuBar(false);

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
	 * Shows a dropdown message box to select something
	 * @param savedGames A list of the saved games
	 * @param text The text of the selector pane
	 * @param title The title of the selctor pane
	 * @return The selected savegame name
	 */
	private String showDropDownSelectorPane(List<String> savedGames, String text, String title) {
		if (savedGames.isEmpty()) {
			JOptionPane.showMessageDialog(mainFrame, "No saved games found!", "", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		String[] data = savedGames.toArray(new String[savedGames.size()]);
		return (String)JOptionPane.showInputDialog(mainFrame, text, title, JOptionPane.QUESTION_MESSAGE, null, data, data[0]);
	}
	
	/**
	 * Initialize the menu bar (NewGame menu item, Quit menu item,...)
	 * @param isGameRunning Set true when calling this Method while running a game
	 */
	private void initMenuBar(boolean isGameRunning) {
        JMenuBar menuBar = new JMenuBar();
		JMenuItem quit;
		JMenuItem single;
		JMenuItem multi;
		JMenuItem load;
		JMenuItem delete;
		
		JMenu menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		
		load = createNewItem("Load game", KeyEvent.VK_L, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String game = showDropDownSelectorPane(controller.getStoredGames(), "Select the saved game", "Load game");
				if(game != null) {
					controller.loadGame(game);
				}
			}
		});
		
		save = createNewItem("Save game", KeyEvent.VK_S, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(true) {
					String name = JOptionPane.showInputDialog("Enter savegame name!");
					if(name == null || name.isEmpty()) {
						break;
					}
					if (!controller.saveGame(name)) {
						JOptionPane.showMessageDialog(mainFrame, "Savegame already taken, choose another one!", "", JOptionPane.WARNING_MESSAGE);
						continue;
					}
					break;
				}
			}
		});
		save.setEnabled(isGameRunning);
		
		delete = createNewItem("Delete game", KeyEvent.VK_D, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String game = showDropDownSelectorPane(controller.getStoredGames(), "Select the saved game", "Delete game");
				if(game != null) {
					int result = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to delete this savegame?", "", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						controller.deleteGame(game);
					}
				}				
			}
		});
		
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
		
		menu.add(single);
		menu.add(multi);
		menu.add(load);
		menu.add(delete);
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
				
		ownFrame.update();
		enemyFrame.update();
		
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
	
	@Override
	public void updateOnLoaded() {
        initMenuBar(true);
        playgroundsPanel.removeAll();
		ownFrame = new PlaygroundFrame(controller, controller.getActivePlayer()); 
		controller.switchPlayer();
		enemyFrame = new PlaygroundFrame(controller, controller.getActivePlayer()); 
		controller.switchPlayer();
		playgroundsPanel.add(ownFrame.get());
		playgroundsPanel.add(enemyFrame.get());
		save.setEnabled(true);
		initMainFrame();
	}
}

