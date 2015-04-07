package nl.focalor.utobot.utopia.model;

import java.util.Date;

public class Attack {
	private Long id;
	private Long personId;
	private String person;
	private Date returnDate;

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

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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
