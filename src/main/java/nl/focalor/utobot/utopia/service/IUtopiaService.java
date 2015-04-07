package nl.focalor.utobot.utopia.service;

import java.util.Date;
import nl.focalor.utobot.utopia.model.UtopiaDate;

public interface IUtopiaService {
	public Date getNextHourChange();

	public int getSecondsTillHourChange();

	public int getHourOfAge();

	public UtopiaDate getUtopiaDate();
}
