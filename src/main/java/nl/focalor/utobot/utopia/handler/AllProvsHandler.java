package nl.focalor.utobot.utopia.handler;

import java.util.Arrays;
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

	public static final List<String> COMMAND_NAMES = Arrays.asList("provall", "provs", "allprovs");

	@Autowired
	private IPersonService personService;

	public AllProvsHandler() {
		super(COMMAND_NAMES);
	}

	@Override
	protected Collection<Person> loadPeople(CommandInput event) {
		return personService.findAll();
	}
}
