package nl.focalor.utobot.utopia.model.entity;

import nl.focalor.utobot.base.model.entity.Person;

import javax.persistence.*;

@Entity
public class SpellCast {

	@Id
	@GeneratedValue
	private Long id;
	private String spellId;
	@ManyToOne
	private Province caster;
	@ManyToOne
	private Province target;
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

	public Province getCaster() {
		return caster;
	}

	public void setCaster(Province caster) {
		this.caster = caster;
	}

	public Province getTarget() {
		return target;
	}

	public void setTarget(Province target) {
		this.target = target;
	}
}
