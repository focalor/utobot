package nl.focalor.utobot.utopia.model;

public class UtopiaDate {
	private int year;
	private int month;
	private int day;
	private int minute;
	private int second;
	private int millisecond;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public String getFormattedMonth() {
		switch (month) {
		case 0:
			return "January";
		case 1:
			return "February";
		case 2:
			return "March";
		case 3:
			return "April";
		case 4:
			return "May";
		case 5:
			return "June";
		case 6:
			return "July";
		default:
			throw new IllegalStateException("Month " + month + " is invalid");
		}
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMinute() {
		return minute;
	}

	public int getMinutesRemaining() {
		if (getSecond() == 0 && getMillisecond() == 0) {
			return 60 - minute;
		} else {
			return 59 - minute;
		}
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public int getSecondsRemaining() {
		if (getMillisecond() == 0) {
			return 60 - second;
		} else {
			return 59 - second;
		}
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getMillisecond() {
		return millisecond;
	}

	public int getMillisecondsRemaining() {
		return 1000 - millisecond;
	}

	public void setMillisecond(int millisecond) {
		this.millisecond = millisecond;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getFormattedMonth());
		builder.append(' ');
		builder.append(getDay());
		builder.append(", YR");
		builder.append(getYear());
		builder.append(" (next tick: ");
		builder.append(getMinutesRemaining());
		builder.append(" minutes and ");
		builder.append(getSecondsRemaining());
		builder.append(" seconds)");
		return builder.toString();
	}
}
