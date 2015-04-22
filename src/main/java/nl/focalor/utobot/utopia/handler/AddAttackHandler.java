package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.service.IAttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddAttackHandler extends AbstractAttackHandler implements ICommandHandler {
	public static final String COMMAND = "attack";
	public static final String[] ALTERNATE_NAMES = {"addattack", "army", "addarmy"};
	private static final List<String> COMMAND_NAMES;

	static {
		COMMAND_NAMES = new ArrayList<>();
		COMMAND_NAMES.add(COMMAND);
		COMMAND_NAMES.addAll(Arrays.asList(ALTERNATE_NAMES));
	}

	@Autowired
	public AddAttackHandler(IAttackService attackService, IPersonService personService) {
		super(attackService, personService);
	}

	@Override
	public String getName() {
		return COMMAND;
	}

	@Override
	public List<String> getCommandNames() {
		return COMMAND_NAMES;
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		double hours = Double.valueOf(event.getArgument());
		int realHours = (int) hours;
		double minutes = 60 * (hours % 1);

		return handleCommand(event, realHours, minutes);
	}

	@Override
	public String getSimpleHelp() {
		return "Adds an attack. Use '!help addattack' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Adds an attack for the user.");
		helpBody.add("USAGE:");
		helpBody.add("!addattack <Hours>");
		helpBody.add("e.g.:");
		helpBody.add("!addattack 4.05");
		return helpBody;
	}

}
