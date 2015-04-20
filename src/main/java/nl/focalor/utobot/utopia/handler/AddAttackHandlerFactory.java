package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.handler.IInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.AttackType;
import nl.focalor.utobot.utopia.service.IAttackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddAttackHandlerFactory implements IInputHandlerFactory {
	private static final String NAME = "rawattack";

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

	private class AttackHandler extends AbstractAttackHandler implements IRegexHandler {
		private final Pattern pattern;
		private final String name;

		public AttackHandler(AttackType attackType) {
			super(attackService, personService);
			pattern = Pattern.compile(attackType.getSyntax());
			name = attackType.getName();
		}

		@Override
		public IResult handleInput(Matcher matcher, IInput input) {
			int hours = Integer.valueOf(matcher.group(1));
			double minutes = (60 * Double.valueOf("0." + matcher.group(2)));

			return handleCommand(input, hours, minutes);
		}

		@Override
		public boolean hasHelp() {
			return true;
		}

		@Override
		public String getSimpleHelp() {
			return "Adds an attack. Use '!help attack' for more info";
		}

		@Override
		public List<String> getHelpBody() {
			List<String> helpBody = new ArrayList<String>();
			helpBody.add("Adds an attack.");
			helpBody.add("USAGE:");
			helpBody.add("!attack <duration>");
			helpBody.add("e.g.:");
			helpBody.add("!attack 10.7");
			return helpBody;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Pattern getRegexPattern() {
			return pattern;
		}
	}

	@Override
	public String getName() {
		return NAME;
	}
}
