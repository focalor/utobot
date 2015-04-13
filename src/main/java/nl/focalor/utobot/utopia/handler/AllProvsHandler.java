package nl.focalor.utobot.utopia.handler;

import java.util.Collection;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.AbstractProvHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllProvsHandler extends AbstractProvHandler {
	public static final String COMMAND_NAME = "provs";
	public static final String[] ALTERNATE_NAMES = {"provall", "allprovs"};

	@Autowired
	private IPersonService personService;

	public AllProvsHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
	}

	@Override
	protected Collection<Person> loadPeople(CommandInput event) {
		return personService.findAll();
	}
}
