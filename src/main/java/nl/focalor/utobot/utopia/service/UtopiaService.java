package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.handler.AddAttackHandlerFactory;
import nl.focalor.utobot.utopia.model.UtopiaDate;
import nl.focalor.utobot.utopia.model.UtopiaSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UtopiaService implements IUtopiaService {

	private static final Pattern pattern = Pattern.compile("(.*) (\\d{1,2}), YR(\\d{1,2})");
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
		return getUtopianDateFromReal(new Date());
	}

	private long getTimeOfAge() {
		long delta = new Date().getTime() - ageStart;
		return delta;
	}

	public Date getRealDateFromUtopian(UtopiaDate utopianDate){
		long weeks = utopianDate.getYear();
		long days = 7 * weeks + utopianDate.getMonth();
		long hours = 24 * days + utopianDate.getDay();
		long minutes = 60 * hours + utopianDate.getMinute();
		long seconds = 60 * minutes + utopianDate.getSecond();
		long miliseconds = 1000 * seconds + utopianDate.getMillisecond();
		long date = miliseconds + ageStart;

		return new Date(date);
	}

	public UtopiaDate getUtopiaDateFromString(String utopianDate){
		Matcher matcher = pattern.matcher(utopianDate);
		UtopiaDate utopiaDate = new UtopiaDate();

		if (matcher.find()) {
			String month = matcher.group(1);
			String day = matcher.group(2);
			String year = matcher.group(3);

			utopiaDate.setDay(Integer.parseInt(day));
			utopiaDate.setMonth(monthFromString(month));
			utopiaDate.setYear(Integer.parseInt(year));
		}
		return utopiaDate;
	}

	private int monthFromString(String month){
		switch (month) {
			case "January":
				return 0;
			case "February":
				return 1;
			case "March":
				return 2;
			case "April":
				return 3;
			case "May":
				return 4;
			case "June":
				return 5;
			case "July":
				return 6;
			default:
				throw new IllegalStateException("Month " + month + " is invalid");
		}
	}

	public Date getRealDateFromUtopianDateString(String utopianDate){
		return getRealDateFromUtopian(getUtopiaDateFromString(utopianDate));
	}

	public UtopiaDate getUtopianDateFromReal(Date realDate){
		long delta = realDate.getTime() - ageStart;
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
}
