package nl.focalor.utobot.utopia.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractGenericCommandHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.utopia.model.entity.Province;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public abstract class AbstractProvHandler extends AbstractGenericCommandHandler {
	private boolean includeNicks = false;

	public AbstractProvHandler(String commandName) {
		super(commandName);
	}

	public AbstractProvHandler(String commandName, String... alternateCommandNames) {
		super(commandName, alternateCommandNames);
	}

	public void setIncludeNicks(boolean includeNicks) {
		this.includeNicks = includeNicks;
	}

	@Override
	@Transactional
	public IResult handleCommand(CommandInput event) {
		Collection<Person> people = loadPeople(event);

		//@formatter:off
		List<String> msgs = people.stream()
				.map(this::toStrings)
				.flatMap(internalMessages -> internalMessages.stream())
				.collect(Collectors.toList());
		//@formatter:on
		return new MultiReplyResult(msgs);
	}

	private List<String> toStrings(Person person) {
		List<String> result = new ArrayList<>();
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
		result.add(msg.toString());

		if (includeNicks) {
			result.add("Known nicknames:");
			//@formatter:off
			result.add(StringUtils.collectionToDelimitedString(
					person.getNicks().stream().map(nick -> nick.getNick()).collect(Collectors.toList())
					, ", "));
			//@formatter:on
		}

		return result;
	}

	abstract protected Collection<Person> loadPeople(CommandInput event);
}
