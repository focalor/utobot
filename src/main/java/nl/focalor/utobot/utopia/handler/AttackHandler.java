package nl.focalor.utobot.utopia.handler;

import java.util.Calendar;
import java.util.regex.Matcher;

import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractRegexHandler;
import nl.focalor.utobot.utopia.model.Attack;
import nl.focalor.utobot.utopia.service.IAttackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttackHandler extends AbstractRegexHandler {
	private static final String REGEX = ".*Taking full control of our new land will take (\\d{1,2})\\.(\\d{1,2}) days\\..*";

	@Autowired
	private IAttackService service;

	public AttackHandler() {
		super(REGEX);
	}

	@Override
	public IResult handleInput(IInput input) {
		Matcher m = getMatcher(input.getInput());
		if (!m.matches()) {
			throw new IllegalStateException(
					"Input does not match expected input");
		}

		int hours = Integer.valueOf(m.group(1));
		double minutesPart = 0.6 * Integer.valueOf(m.group(2));
		int minutes = (int) minutesPart;
		int seconds = (int) ((minutesPart - minutes) * 60);

		Attack attack = new Attack();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hours);
		cal.add(Calendar.MINUTE, minutes);
		cal.add(Calendar.SECOND, seconds);

		attack.setReturnDate(cal.getTime());
		service.add(attack);

		return new ReplyResult("Attack added for " + hours + " hour");
	}
}
