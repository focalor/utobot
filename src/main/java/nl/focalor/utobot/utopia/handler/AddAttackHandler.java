package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.handler.IGenericCommandHandler;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.service.IAttackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddAttackHandler extends AbstractAttackHandler implements IGenericCommandHandler {
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
		String[] parts = StringUtils.split(event.getArgument());
		int index = getFirstDoubleIndex(parts);

		final String nick;
		final double time;
		final String comment;

		if (index == 0) {
			nick = event.getUser();
			time = Double.valueOf(parts[0]);
			if (parts.length > 1) {
				comment = StringUtils.join(parts, ' ', 1, parts.length);
			} else {
				comment = null;
			}
		} else if (index == 1) {
			nick = parts[0];
			time = Double.valueOf(parts[1]);

			if (parts.length > 2) {
				comment = StringUtils.join(parts, ' ', 2, parts.length);
			} else {
				comment = null;
			}
		} else {
			return new ErrorResult("Input not recognized");
		}

		return handleCommand(nick, time, comment);
	}

	private int getFirstDoubleIndex(String[] parts) {
		for (int i = 0; i < parts.length; i++) {
			try {
				Double.valueOf(parts[i]);
				return i;
			} catch (NumberFormatException ex) {
			}
		}

		return -1;
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
		helpBody.add("!addattack [Nick] <Hours> [Comment]");
		helpBody.add("e.g.:");
		helpBody.add("!addattack 4.05 this is a comment");
		helpBody.add("!addattack Sephi 4.05 yay");
		return helpBody;
	}

}
