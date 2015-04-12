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
public class WhoisHandler extends AbstractProvHandler {
	public static final List<String> COMMAND_NAMES = Arrays.asList("whois", "prov");

	@Autowired
	private IPersonService personService;

	public WhoisHandler() {
		super(COMMAND_NAMES);
	}

	@Override
	protected Collection<Person> loadPeople(CommandInput event) {
		String search = event.getArgument();
		return personService.findByNickNameOrProvince(search);
	}

}
