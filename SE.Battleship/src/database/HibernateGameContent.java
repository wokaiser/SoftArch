package database;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grid")
public class HibernateGameContent implements Serializable {

	private static final long serialVersionUID = 1538704903825440126L;

	@Id
	@Column(name = "id")
	private String gridId;

	@OneToMany(mappedBy = "grid")
	@Column(name = "cell")
	private List<HibernatePlaygroundItem> cells;
	
	private String name;
	private int blocksPerEdge;
	private int numberSetCells;	
	
	public HibernateGameContent() {
	}

	public String getId() {
		return gridId;
	}

	public void setId(String id) {
		this.gridId = id;
	}

	public List<HibernatePlaygroundItem> getCells() {
		return cells;
	}

	public void setCells(List<HibernatePlaygroundItem> cells) {
		this.cells = cells;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if(name != null) this.name = name;
	}
	
	public int getBlocksPerEdge() {
		return blocksPerEdge;
	}

	public void setBlocksPerEdge(int blocksPerEdge) {
		this.blocksPerEdge = blocksPerEdge;
	}
	
	public int getNumberSetCells() {
		return numberSetCells;
	}

	public void setNumberSetCells(int numberSetCells) {
		this.numberSetCells = numberSetCells;
	}
}
