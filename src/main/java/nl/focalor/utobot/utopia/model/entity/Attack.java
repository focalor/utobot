package nl.focalor.utobot.utopia.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nl.focalor.utobot.base.model.entity.Person;

import org.apache.commons.lang3.StringUtils;

@Entity
public class Attack {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(optional = false)
	private Person person;
	@Column(nullable = false)
	private Date returnDate;
	private String comment;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return toString(true);
	}

	public String toString(boolean includeAttacker) {
		String attacker = getPerson().getName();

		long deltaSeconds = (getReturnDate().getTime() - new Date().getTime()) / 1000;
		int deltaMinutes = (int) (deltaSeconds / 60);
		int deltahours = deltaMinutes / 60;

		int seconds = (int) (deltaSeconds % 60);
		int minutes = deltaMinutes % 60;

		StringBuilder builder = new StringBuilder();
		builder.append(attacker);
		builder.append("'s army is out for ");
		builder.append(deltahours);
		builder.append("h ");
		builder.append(minutes);
		builder.append("m ");
		builder.append(seconds);
		builder.append("s");

		if (!StringUtils.isEmpty(comment)) {
			builder.append(" (");
			builder.append(comment);
			builder.append(')');
		}
		return builder.toString();
	}
}
