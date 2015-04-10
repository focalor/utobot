package nl.focalor.utobot.utopia.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.base.model.Person;

import org.apache.commons.lang3.text.WordUtils;

public abstract class AbstractProvHandler extends AbstractCommandHandler {

	public AbstractProvHandler(List<String> commandNames) {
		super(commandNames);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		Collection<Person> people = loadPeople(event);
		List<String> msgs = people.stream().map(this::toString).collect(Collectors.toList());
		return new MultiReplyResult(msgs);
	}

	private String toString(Person person) {
		StringBuilder msg = new StringBuilder();
		msg.append(person.getName());

		Province prov = person.getProvince();
		if (prov != null) {
			msg.append(" - ");
			msg.append(prov.getName());
			msg.append(" [");
			msg.append(WordUtils.capitalizeFully(prov.getRace().name()));
			msg.append(" / ");
			msg.append(WordUtils.capitalizeFully(prov.getPersonality().name()));
			msg.append(']');
		}

		return msg.toString();
	}

	abstract protected Collection<Person> loadPeople(CommandInput event);
}
