package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.UtopiaDate;

import java.util.Date;

public interface IUtopiaService {
	public Date getNextHourChange();

	public int getSecondsTillHourChange();

	public int getHourOfAge();

	public UtopiaDate getUtopiaDate();
}
