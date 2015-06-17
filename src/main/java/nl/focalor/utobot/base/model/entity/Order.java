package nl.focalor.utobot.base.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Race;

@Entity(name = "Orders")
public class Order {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String text;
	private Race race;
	private Personality personality;
	@ManyToOne
	private Person person;
	private Integer lastHour;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getLastHour() {
		return lastHour;
	}

	public void setLastHour(Integer lastHour) {
		this.lastHour = lastHour;
	}

}