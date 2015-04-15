package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author focalor
 */
@Component
public class AddNickHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "addnick";
	@Autowired
	private IPersonService personService;

	public AddNickHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		String[] args = event.getArgument().split(" ");
		if (args.length == 0) {
			return new ReplyResult("Invalid call, check syntax");
		} else if (args.length == 1) {
			return addNicks(event.getSource(), args, 0);
		} else {
			return addNicks(args[0], args, 1);
		}
	}

	public IResult addNicks(String name, String[] nicks, int startIndex) {
		Person person = personService.find(name, false);
		if (person == null) {
			return new ReplyResult("Cannot find " + name);
		}

		for (int i = startIndex; i < nicks.length; i++) {
			personService.addNick(person.getId(), nicks[i]);
		}

		return new ReplyResult("Nickname(s) added");
	}

	@Override
	public boolean hasHelp() {
		return true;
	}

	@Override
	public String getSimpleHelp() {
		return "Adds a nick to a User. Use '!help addnick' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Adds a nick to a User.");
		helpBody.add("USAGE:");
		helpBody.add("!addnick <User> <Nick>");
		helpBody.add("e.g.:");
		helpBody.add("!addnick Sephi DarkSephi");
		return helpBody;
	}
}
