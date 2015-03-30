package nl.focalor.utobot.utopia.model;

import java.util.Date;

public class Attack {
	private Long id;
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

}
