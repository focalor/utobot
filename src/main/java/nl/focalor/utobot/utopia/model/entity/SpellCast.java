package nl.focalor.utobot.utopia.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import nl.focalor.utobot.base.model.entity.Person;

@Entity
public class SpellCast {

	@Id
	@GeneratedValue
	private Long id;
	private String spellId;
	@ManyToOne
	private Person caster;
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

	public Person getCaster() {
		return caster;
	}

	public void setCaster(Person caster) {
		this.caster = caster;
	}

	public Province getTarget() {
		return target;
	}

	public void setTarget(Province target) {
		this.target = target;
	}
}
