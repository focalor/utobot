package nl.focalor.utobot.utopia.model;

public class Province {
	private Long id;
	private Long personId;
	private String name;
	private Integer island;
	private Integer kingdom;
	private Race race;
	private Personality personality;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
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

}
