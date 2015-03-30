package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.model.Person;
import nl.focalor.utobot.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Province;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WhoisHandler extends AbstractCommandHandler {
	public static final List<String> COMMAND_NAMES = Arrays.asList("whois",
			"prov");

	@Autowired
	private IPersonService personService;

	public WhoisHandler() {
		super(COMMAND_NAMES);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		String search = event.getArgument();
		List<Person> people = personService.loadPeople(search, search);
		return mapToReply(people);
	}

	private MultiReplyResult mapToReply(List<Person> people) {
		List<String> messages = new ArrayList<String>();
		for (Person person : people) {
			StringBuilder msg = new StringBuilder();
			msg.append(person.getName());

			Province prov = person.getProvince();
			if (prov != null) {
				msg.append(" - ");
				msg.append(prov.getName());
				msg.append(" [");
				msg.append(WordUtils.capitalizeFully(prov.getRace().name()));
				msg.append(" / ");
				msg.append(WordUtils.capitalizeFully(prov.getPersonality()
						.name()));
				msg.append(']');
			}
			messages.add(msg.toString());
		}
		return new MultiReplyResult(messages);
	}
}
