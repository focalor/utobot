package nl.focalor.utobot.utopia.model;

import java.util.List;

/**
 * @author focalor
 */
public class UtopiaSettings {
	private String startDate;
	private List<AttackType> attacks;
	private List<SpellType> spells;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public List<AttackType> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<AttackType> attacks) {
		this.attacks = attacks;
	}

	public List<SpellType> getSpells() {
		return spells;
	}

	public void setSpells(List<SpellType> spells) {
		this.spells = spells;
	}

}
