package nl.focalor.utobot.utopia.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author focalor
 */
public class ShowProvsHandlers extends AbstractCommandHandler {
	public static final List<String> COMMAND_NAMES = Arrays.asList("provall", "provs");
	@Autowired
	private IPersonService personService;

	public ShowProvsHandlers() {
		super(COMMAND_NAMES);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		List<Person> people = personService.load();
		List<String> messages = people.stream().map(this::mapPerson).collect(Collectors.toList());

		return new MultiReplyResult(messages);
	}

	private String mapPerson(Person p) {
		// TODO
		return "";
	}
}
