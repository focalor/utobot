package nl.focalor.utobot.utopia.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nl.focalor.utobot.utopia.handler.AddAttackHandlerFactory;
import nl.focalor.utobot.utopia.model.UtopiaDate;
import nl.focalor.utobot.utopia.model.UtopiaSettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtopiaService implements IUtopiaService {
	private final long ageStart;
	@Autowired
	private AddAttackHandlerFactory handler;

	@Autowired
	public UtopiaService(UtopiaSettings settings) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date parsedDate = formatter.parse(settings.getStartDate());
		this.ageStart = parsedDate.getTime();
	}

	@Override
	public Date getNextHourChange() {
		return new Date(new Date().getTime() + getSecondsTillHourChange()
				* 1000);
	}

	@Override
	public int getSecondsTillHourChange() {
		long timeOfAgeInSeconds = getTimeOfAge() / 1000;
		int secondsIntoHour = (int) (timeOfAgeInSeconds % (60 * 60));
		return 60 * 60 - secondsIntoHour;
	}

	@Override
	public int getHourOfAge() {
		return (int) (getTimeOfAge() / (1000 * 60 * 60));
	}

	@Override
	public UtopiaDate getUtopiaDate() {
		long delta = getTimeOfAge();
		long seconds = delta / 1000;
		long millisecondsSpare = delta % 1000;

		long minutes = seconds / 60;
		long secondsSpare = seconds % 60;

		long hours = minutes / 60;
		long minutesSpare = minutes % 60;

		long days = hours / 24;
		long hoursSpare = hours % 24;

		long weeks = days / 7;
		long daysSpare = days % 7;

		UtopiaDate result = new UtopiaDate();
		result.setYear((int) weeks);
		result.setMonth((int) daysSpare);
		result.setDay((int) hoursSpare);
		result.setMinute((int) minutesSpare);
		result.setSecond((int) secondsSpare);
		result.setMillisecond((int) millisecondsSpare);
		return result;
	}

	private long getTimeOfAge() {
		long delta = new Date().getTime() - ageStart;
		return delta;
	}
}
