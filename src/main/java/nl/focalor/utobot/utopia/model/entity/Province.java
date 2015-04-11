package nl.focalor.utobot.utopia.model.entity;

import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Race;

import javax.persistence.*;

@Entity
public class Province {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Integer island;
	private Integer kingdom;
	private Race race;
	private Personality personality;
	@OneToOne(mappedBy="province")
	private Person owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsland() {
		return island;
	}

	public void setIsland(Integer island) {
		this.island = island;
	}

	public Integer getKingdom() {
		return kingdom;
	}

	public void setKingdom(Integer kingdom) {
		this.kingdom = kingdom;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}
}
