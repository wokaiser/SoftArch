package database;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import controller.GameContent;
import static org.junit.Assert.*;

public class PersistentGameContentTest {
	private final String ID = "1";
	private final int COLUMNS = 10;
    private final int ROWS = 22;
    
    @Test
	public void testSetterAndGetter() throws Exception {
        List<PersistentPlaygroundItem> playground1 = new LinkedList<PersistentPlaygroundItem>();
        playground1.add(new PersistentPlaygroundItem());
        List<PersistentPlaygroundItem> playground2 = new LinkedList<PersistentPlaygroundItem>();
	    PersistentGameContent content = new PersistentGameContent();
	    content.setId(ID);
	    content.setColumns(COLUMNS);
	    content.setRows(ROWS);      
	    content.setGameType(GameContent.SINGLEPLAYER);
	    content.setPlayer1(GameContent.HUMAN_PLAYER_1);
	    content.setPlayer2(GameContent.AI_PLAYER_1_EASY);
	    content.setPlayground1(null);
	    content.setPlayground2(null);

	    assertTrue(content.getId() == ID);
	    assertTrue(content.getColumns() == COLUMNS);
	    assertTrue(content.getRows() == ROWS);
	    assertTrue(content.getGameType() == GameContent.SINGLEPLAYER);
	    assertTrue(content.getPlayer1() == GameContent.HUMAN_PLAYER_1);
	    assertTrue(content.getPlayer2() == GameContent.AI_PLAYER_1_EASY);
	    assertTrue(content.getPlayground1() == null);
	    assertTrue(content.getPlayground2() == null);
	    content.setPlayground1(playground1);
	    content.setPlayground2(playground2);
	    assertTrue(content.getPlayground1() == playground1);
        assertTrue(content.getPlayground2() == playground2);
	}
}
