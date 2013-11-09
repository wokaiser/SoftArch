package database;

import database.GameContent;
import database.HibernateGameContent;

public class HibernateMapping {
	
	private HibernateMapping() {
	}
	
	public static GameContent map(HibernateGameContent content) {
		return null;
	}
	
	public static HibernateGameContent map(GameContent content) {
	//	HibernateGameContent hContent = new HibernateGameContent(content.getName());
	//	hContent.setActivePlayer(content.getActivePlayer());
		/*	hContent.setGameType(content.getGameType());
		
		
		
		hContent.setEnemyPlayer(content.getEnemyPlayer());
		
		hContent.setOwnPlayground(null);*/

		HibernateGameContent hContent = new HibernateGameContent();
		hContent.setId("new_ID");
		
		return hContent;
	}
}