package nl.focalor.utobot.utopia.handler;

import java.util.Calendar;

import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.entity.Attack;
import nl.focalor.utobot.utopia.service.IAttackService;

public abstract class AbstractAttackHandler {
	private final IAttackService attackService;
	private final IPersonService personService;

	public AbstractAttackHandler(IAttackService attackService, IPersonService personService) {
		this.attackService = attackService;
		this.personService = personService;
	}

	public IResult handleCommand(String personNick, double time, String comment) {
		// Gather data
		Person person = personService.find(personNick, true);
		if (person == null) {
			return new ErrorResult("Unrecognized player, register your province/nick");
		}
		int hours = (int) time;
		double minutes = 60 * (time % 1);
		Calendar returnDate = calculateReturnDate(hours, minutes);

		// Create attack model
		Attack attack = new Attack();
		attack.setReturnDate(returnDate.getTime());
		attack.setPerson(person);
		attack.setComment(comment);
		attackService.create(attack, true);

		// Create response
		StringBuilder builder = new StringBuilder();
		builder.append("Attack added for ");
		builder.append(person.getName());
		builder.append(" for ");
		builder.append(hours + (minutes / 60));
		builder.append(" hour");
		return new ReplyResult(builder.toString());
	}

	private Calendar calculateReturnDate(int hours, double minutes) {
		int realMinutes = (int) minutes;
		int seconds = (int) ((minutes - realMinutes) * 60);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hours);
		cal.add(Calendar.MINUTE, realMinutes);
		cal.add(Calendar.SECOND, seconds);

		return cal;
	}

}
