package database.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
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
	public Integer id;

	@Column(name = "playground")
	public Integer playground = 0;
	
	@Column(name = "rowcell")
	public Integer rowcell = 0;

	@Column(name = "columncell")
	public Integer columncell = 0;

	@ManyToOne
	@JoinColumn(name = "gameContentid")
	public HibernateGameContent gameContent;
	
	public HibernatePlaygroundItem(HibernateGameContent gameContent, Integer playground, Integer column, Integer row) {
		this.gameContent = gameContent;
		this.playground = playground;
		this.columncell = column;
		this.rowcell = row;
	}
	public HibernatePlaygroundItem() {
		
	}
	
	public HibernateGameContent getGameContent() {
		return gameContent;
	}

	public void setGameContent(HibernateGameContent gameContent) {
		this.gameContent = gameContent;
	}
	
	public Integer getRowcell() {
		return rowcell;
	}

	public void setRowcell(Integer rowcell) {
		this.rowcell = rowcell;
	}

	public Integer getColumncell() {
		return columncell;
	}

	public void setColumncell(Integer columncell) {
		this.columncell = columncell;
	}
	
	public Integer getPlayground() {
		return playground;
	}

	public void setPlayground(Integer playground) {
		this.playground = playground;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
}
