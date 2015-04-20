package nl.focalor.utobot.utopia.handler;

import java.util.Collection;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.AbstractProvHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WhoisHandler extends AbstractProvHandler {
	public static final String COMMAND_NAME = "whois";
	public static final String[] ALTERNATE_NAMES = {"prov"};

	@Autowired
	private IPersonService personService;

	public WhoisHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
		setIncludeNicks(true);
	}

	@Override
	protected Collection<Person> loadPeople(CommandInput event) {
		String search = event.getArgument();
		return personService.findByNickNameOrProvince(search);
	}

}
