package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.AbstractProvHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllProvsHandler extends AbstractProvHandler {
	public static final String COMMAND_NAME = "provs";
	public static final String[] ALTERNATE_NAMES = { "provall", "allprovs" };

	@Autowired
	private IPersonService personService;

	public AllProvsHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
	}

	@Override
	protected Collection<Person> loadPeople(CommandInput event) {
		return personService.findAll();
	}

	@Override
	public String getSimpleHelp() {
		return "Lists all provinces registered with the bot. Use '!help provs' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Lists all provinces registered with the bot.");
		helpBody.add("USAGE:");
		helpBody.add("!provs");
		return helpBody;
	}
}
