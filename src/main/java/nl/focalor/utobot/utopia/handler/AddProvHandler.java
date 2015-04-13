package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Race;
import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AddProvHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "addprov";
	private static final Pattern pattern = Pattern.compile("(.*) - (.*) \\[(.*)/(.*)\\]");

	@Autowired
	private IPersonService personService;
	@Autowired
	private IProvinceService provinceService;

	public AddProvHandler() {
		super(COMMAND_NAME);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public IResult handleCommand(CommandInput event) {
		Matcher matcher = pattern.matcher(event.getArgument());
		if (matcher.find()) {
			createProvince(matcher);
			return new ReplyResult("Province added");
		} else {
			return new ReplyResult("Province could not added, check syntax: PLAYER - PROVINCE [RACE/PERSONALITY]");
		}
	}

	private void createProvince(Matcher matches) {
		String player = matches.group(1);
		String prov = matches.group(2);
		Race race = parseRace(matches.group(3));
		Personality personality = parsePersonality(matches.group(4));

		if (race == null || personality == null) {
			throw new IllegalStateException("race (" + matches.group(3) + ") or personality (" + matches.group(4)
					+ ") invalid");
		}

		Person person = new Person();
		person.setName(player);

		Province province = new Province();
		province.setName(prov);
		province.setOwner(person);
		province.setRace(race);
		province.setPersonality(personality);
		provinceService.create(province);

		person.setProvince(province);
		personService.save(person);
	}

	private Race parseRace(String name) {
		for (Race race : Race.values()) {
			if (race.name().equalsIgnoreCase(name)) {
				return race;
			}
		}
		return null;
	}

	private Personality parsePersonality(String name) {
		for (Personality personality : Personality.values()) {
			if (personality.name().equalsIgnoreCase(name)) {
				return personality;
			}
		}
		return null;
	}
}
