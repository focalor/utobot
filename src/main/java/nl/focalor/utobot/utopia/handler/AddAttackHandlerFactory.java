package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import javax.annotation.PostConstruct;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractRegexHandler;
import nl.focalor.utobot.base.input.handler.IInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.base.model.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Attack;
import nl.focalor.utobot.utopia.model.AttackType;
import nl.focalor.utobot.utopia.service.IAttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddAttackHandlerFactory implements IInputHandlerFactory {
	private final List<IRegexHandler> handlers = new ArrayList<>();

	@Autowired
	private IAttackService attackService;
	@Autowired
	private IPersonService personService;

	@PostConstruct
	public void init() {
		for (AttackType attackType : attackService.getKnownAttackTypes()) {
			handlers.add(new AttackHandler(attackType));
		}
	}

	@Override
	public List<IRegexHandler> getRegexHandlers() {
		return handlers;
	}

	private class AttackHandler extends AbstractRegexHandler {

		public AttackHandler(AttackType attackType) {
			super(attackType.getSyntax());
		}

		@Override
		public IResult handleInput(IInput input) {
			// Handle input
			Matcher matcher = getMatcher(input);
			if (!matcher.find()) {
				throw new IllegalStateException("Input does not match expected input");
			}
			int hours = Integer.valueOf(matcher.group(1));
			double minutes = 60 * Double.valueOf("0." + matcher.group(2));

			// Gather data
			Calendar returnDate = calculateReturnDate(hours, minutes);
			Person person = personService.find(input.getSource(), true);

			// Create attack model
			Attack attack = new Attack();
			attack.setReturnDate(returnDate.getTime());
			if (person == null) {
				attack.setPerson(input.getSource());
			} else {
				attack.setPersonId(person.getId());
			}
			attackService.create(attack, true);

			// Create response
			StringBuilder builder = new StringBuilder();
			builder.append("Attack added for ");
			builder.append(input.getSource());
			builder.append(" for ");
			builder.append(hours);
			builder.append('.');
			builder.append(matcher.group(2));
			builder.append(" hours");
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
}
