package database.hibernate;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "playgroundItem")
public class HibernatePlaygroundItem implements Serializable {
	private static final long serialVersionUID = 3184225396652683648L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int playground;
	private int rowcell;
	private int columncell;
	private char status;

	@ManyToOne
	@JoinColumn(name = "gameContentid")
	public HibernateGameContent gameContent;
	
	public HibernatePlaygroundItem(HibernateGameContent gameContent, int playground, int rowcell, int columncell, char status) {
		this.gameContent = gameContent;
		this.playground = playground;
		this.rowcell = rowcell;
		this.columncell = columncell;
		this.status = status;
	}
	public HibernatePlaygroundItem() {
		
	}
	
	public HibernateGameContent getGameContent() {
		return gameContent;
	}

	public void setGameContent(HibernateGameContent gameContent) {
		this.gameContent = gameContent;
	}
	
	public int getRowcell() {
		return rowcell;
	}

	public void setRowcell(int rowcell) {
		this.rowcell = rowcell;
	}

	public int getColumncell() {
		return columncell;
	}

	public void setColumncell(int columncell) {
		this.columncell = columncell;
	}
	
	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	public int getPlayground() {
		return playground;
	}

	public void setPlayground(int playground) {
		this.playground = playground;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
}
