package view.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.general.Constances;
import model.general.Resources;
import model.playground.Coordinates;
import controller.GameController;

/** 
 * The Playground can return a JPanel, to display a playground. The view of the
 * panel depends on the actual controller state (hide ships if enemy player will
 * get the playground).
 */
public class PlaygroundFrame implements ActionListener {
	
	private GameController controller;
	private JButton [][] ownCells;
	private JButton [][] enemyCells;
	private JPanel ownPanel;
	private JPanel enemyPanel;
	private Coordinates target;
	private String player;	

	/**
	 * Create a PlaygroundFrame object.
	 * @param controller The game controller which will be used
	 * @param player The name of the actual player
	 */
	public PlaygroundFrame(final GameController controller, String player) {
		this.controller = controller;
		this.target = new Coordinates(controller.getRows(), controller.getColumns());
		this.player = player;
		this.ownCells = new JButton [this.controller.getRows()][this.controller.getColumns()];
		this.enemyCells = new JButton [this.controller.getRows()][this.controller.getColumns()];
		this.ownPanel = new JPanel(new GridLayout(controller.getRows(), controller.getColumns()));
		this.enemyPanel = new JPanel(new GridLayout(controller.getRows(), controller.getColumns()));
		this.initFrame(this.getActivePlayground());
	}
	
	/**
	 * Initialize the frame, which contains the playground
	 * @param playground The playground to save.
	 */
	private void initFrame(char [][] playground) {
	    for (int row = 0; row < this.controller.getRows(); row++) {
	    	for (int column = 0; column < this.controller.getColumns(); column++) {
	    		initCells(playground, row, column);
	    	}
	    }
	}
	
	/**
	 * Initialize the cells, which represent one element of the playground
	 * @param playground The playground to initialize.
	 * @param row The number of rows of the playground
	 * @param column The number of column of the playground
	 */
	private void initCells(char [][] playground, int row, int column) {		
		ownCells[row][column] = new JButton(getCellIcon(playground[row][column], Resources.CELL_SHIP_ICON));
		enemyCells[row][column] = new JButton(getCellIcon(playground[row][column], Resources.CELL_INIT_ICON));
    	enemyCells[row][column].addActionListener(this);	
		ownCells[row][column].setPreferredSize(new Dimension(Resources.ICON_WIDTH, Resources.ICON_HEIGHT));
		enemyCells[row][column].setPreferredSize(new Dimension(Resources.ICON_WIDTH, Resources.ICON_HEIGHT));
		ownPanel.add(ownCells[row][column]);
		enemyPanel.add(enemyCells[row][column]);
	}
	
	/**
	 * Get the Image representing a state of the playground(e.g. MATRIX_HIT, MATRIX_MISS,..)
	 * @param playground The playground to initialize.
	 * @param row The number of rows of the playground
	 * @param column The number of column of the playground
	 */
	private ImageIcon getCellIcon(char cell, ImageIcon defaultIcon) {
		int [] states = {Constances.MATRIX_INIT, Constances.MATRIX_HIT, Constances.MATRIX_MISS};
		ImageIcon [] icons = {Resources.CELL_INIT_ICON, Resources.CELL_HIT_ICON, Resources.CELL_MISS_ICON};
		for (int i = 0; i < states.length; i++) {
			if (states[i] == cell) {
				return icons[i];
			}
		}
		/* default return */
		return defaultIcon;
	}
	
	/**
	 * Get the name of the player, which owns this playground
	 * @return The name of the player as a string
	 */
	public String getPlayer() {
		return player;
	}
	
	/**
	 * Get the JPanel (enemy playground view, or active player playground view depends on controller state).
	 */
	public JPanel get() {
		if (controller.getActivePlayer() == this.player) {
			ownPanel.setBorder(BorderFactory.createTitledBorder("Own Playground of " + this.player));
			return ownPanel;
		}
		enemyPanel.setBorder(BorderFactory.createTitledBorder("Enemy Playground"));
		return enemyPanel;
	}
	
	/**
	 * In some cases the playground (own and enemy) can get out of sync.
	 * To sync them call this method. The playground will be updated
	 * with the playground from the controller.
	 */
	public void update() {
		char [][] playground = getActivePlayground();

	    for (int row = 0; row < this.controller.getRows(); row++) {
	    	for (int column = 0; column < this.controller.getColumns(); column++) {
				ownCells[row][column].setIcon(getCellIcon(playground[row][column], Resources.CELL_SHIP_ICON));
				enemyCells[row][column].setIcon(getCellIcon(playground[row][column], Resources.CELL_INIT_ICON));
	    	}
	    }
	}
	
	/**
	 * Get the actual active playground as a matrix from the controller
	 * @param playground matrix
	 */
	private char [][] getActivePlayground() {
		if (controller.getActivePlayer() == this.player) {
			return this.controller.getOwnPlaygroundAsMatrix();
		}
		return this.controller.getEnemyPlaygroundAsMatrix();
	}

	/**
	 * Action handler to react on clicking to the playground.
	 * @param event The occurred event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	    for (int row = 0; row < this.controller.getRows(); row++) {
	    	for (int column = 0; column < this.controller.getColumns(); column++) {
				/* find cell which was clicked */
				if(e.getSource() == enemyCells[row][column]){
					target.setRow(row);
					target.setColumn(column);
					controller.shoot(controller.getActivePlayer(), target);
					update();
					controller.gameFinished();
				}
	    	}
		}
	}
}