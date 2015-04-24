package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class AddSpellHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "addspell";
	public static final String[] ALTERNATE_NAMES = {"spell"};

	@Autowired
	private IPersonService personService;
	@Autowired
	private ISpellService spellService;

	public AddSpellHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		String[] input = StringUtils.split(event.getArgument());

		Person person = personService.find(event.getSource(), true);
		if (person == null) {
			return new ErrorResult("Person not recognized");
		}

		SpellCast cast = new SpellCast();
		cast.setCaster(person);
		cast.setLastHour(Integer.valueOf(input[1]));
		cast.setSpellId(input[0]);
		cast.setTarget(person.getProvince());

		spellService.create(cast, true);
		return new ReplyResult("Spell added");
	}

	@Override
	public String getSimpleHelp() {
		return "Adds a spells for the user. Use '!help addspell' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Adds a spells for the user.");
		helpBody.add("USAGE:");
		helpBody.add("!addspell <spell> <hours>");
		helpBody.add("e.g.:");
		helpBody.add("!addspell L&P 12");
		return helpBody;
	}
}
