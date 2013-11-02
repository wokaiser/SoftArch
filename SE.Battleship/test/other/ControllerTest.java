package other;

import static org.junit.Assert.*;
import model.general.Constances;
import model.general.Status;
import model.playground.Coordinates;

import org.junit.*;

import controller.GameController;
import database.GameContent;

public class ControllerTest {
	
	private GameController controller;

	@Before
	public void setUp() {
		controller = new GameController(null);
		controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, GameController.HUMAN_PLAYER_1, GameController.AI_PLAYER_1, GameContent.SINGLEPLAYER);
	}
	
	@Test
	public void testGameController() {
		try {
			new GameController(null);
		} catch (Exception exc) {
			fail("Should not throw exception at this point.");
		}
	}
	
	@Test
	public void testinitController() {
		try {
			controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, GameController.HUMAN_PLAYER_1, GameController.AI_PLAYER_1, GameContent.SINGLEPLAYER);
		} catch (Exception exc) {
			fail("Should not throw exception at this point.");
		}
	}
	
	@Test
	public void testgetGameType() {
		assertTrue(controller.getGameType() == GameContent.SINGLEPLAYER);
	}
	
	@Test
	public void testgameFinished() {
		assertFalse(controller.gameFinished());
	}
	
	@Test
	public void testgetStatus() {
		Status mock = new Status();
		mock.addText("Welcome to Battleships");
		Status result = controller.getStatus();
		
		assertTrue(mock.getErrorCount() == result.getErrorCount());
		assertEquals(mock.getError(), result.getError());
		
		assertTrue(mock.getTextCount() == result.getTextCount());
		assertEquals(mock.getText(), result.getText());
	}
	
	@Test
	public void testshoot() {
		Coordinates tmp = new Coordinates(5 ,5);
		int result = controller.shoot(controller.getActivePlayer(), tmp);
		assertTrue(result >= Constances.SHOOT_HIT && result <= Constances.SHOOT_INVALID);
	}
	
	@Test
	public void testgetActivePlayer() {
		String player = controller.getActivePlayer();
		assertEquals(player, GameController.HUMAN_PLAYER_1);
		String enemy = controller.getEnemyPlayer();
		assertEquals(enemy, GameController.AI_PLAYER_1);
	}
	
	@Test
	public void testgetRows() {
		int rows = controller.getRows();
		assertEquals(rows, Constances.DEFAULT_ROWS);
	}
	
	@Test
	public void testgetColumns() {
		int columns = controller.getColumns();
		assertEquals(columns, Constances.DEFAULT_COLUMNS);
	}
	
	@Test
	public void testgetEnemyPlaygroundAsString() {
		String playground = controller.getEnemyPlaygroundAsString();
		assertNotNull(playground);
		assertTrue(playground.length() > 0);
	}
	
	@Test
	public void testgetEnemyPlaygroundAsMatrix() {
		char[][] playground = controller.getEnemyPlaygroundAsMatrix();
		assertNotNull(playground);
		assertEquals(playground.length, Constances.DEFAULT_ROWS);
		assertEquals(playground[0].length, Constances.DEFAULT_COLUMNS);
	}
	
	@Test
	public void testgetOwnPlaygroundAsString() {
		String playground = controller.getOwnPlaygroundAsString();
		assertNotNull(playground);
		assertTrue(playground.length() > 0);
	}

	@Test
	public void testgetOwnPlaygroundAsMatrix() {
		char[][] playground = controller.getOwnPlaygroundAsMatrix();
		assertNotNull(playground);
		assertEquals(playground.length, Constances.DEFAULT_ROWS);
		assertEquals(playground[0].length, Constances.DEFAULT_COLUMNS);
	}
}
