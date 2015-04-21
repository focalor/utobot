package nl.focalor.utobot.utopia.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nl.focalor.utobot.base.model.entity.Person;

@Entity
public class Attack {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(optional = false)
	private Person person;
	@Column(nullable = false)
	private Date returnDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		long deltaSeconds = (getReturnDate().getTime() - new Date().getTime()) / 1000;
		int deltaMinutes = (int) (deltaSeconds / 60);
		int deltahours = deltaMinutes / 60;

		int seconds = (int) (deltaSeconds % 60);
		int minutes = deltaMinutes % 60;

		StringBuilder builder = new StringBuilder();
		builder.append("Army out for ");
		builder.append(deltahours);
		builder.append("h ");
		builder.append(minutes);
		builder.append("m ");
		builder.append(seconds);
		builder.append("s");
		return builder.toString();
	}
}
