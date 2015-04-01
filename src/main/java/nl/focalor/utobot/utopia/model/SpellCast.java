package nl.focalor.utobot.utopia.model;

public class SpellCast {
	private Long id;
	private String spellId;
	private Integer lastHour;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpellId() {
		return spellId;
	}

	public void setSpellId(String spellId) {
		this.spellId = spellId;
	}

	public Integer getLastHour() {
		return lastHour;
	}

	public void setLastHour(Integer lastHour) {
		this.lastHour = lastHour;
	}

}
