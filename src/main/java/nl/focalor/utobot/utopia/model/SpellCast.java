package nl.focalor.utobot.utopia.model;

import java.util.Date;

public class SpellCast {
	private Long id;
	private Spell spell;
	private Date endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Spell getSpell() {
		return spell;
	}

	public void setSpell(Spell spell) {
		this.spell = spell;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
