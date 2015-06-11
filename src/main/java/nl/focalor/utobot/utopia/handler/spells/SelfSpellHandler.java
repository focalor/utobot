package nl.focalor.utobot.utopia.handler.spells;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

/**
 * @author focalor
 */
public class SelfSpellHandler extends AbstractSpellHandler {
	private final IUtopiaService utopiaService;
	private final IPersonService personService;
	private final ISpellService spellService;
	private final Spell spell;

	public SelfSpellHandler(IUtopiaService utopiaService, IPersonService personService, ISpellService spellService,
			Spell spell) {
		super(spell.getName(), spell.getSelfSyntax());
		this.utopiaService = utopiaService;
		this.personService = personService;
		this.spellService = spellService;
		this.spell = spell;
	}

	@Override
	public IResult handleInput(Matcher matcher, IInput input) {
		// Get duration
		String matchedGroup = matcher.group(1);

		final Integer duration;
		if ("until the end of this day".equals(matchedGroup)) {
			duration = 0;
		} else {
			String reg = "for (\\d{1,2}) day";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher2 = pattern.matcher(matchedGroup);
			boolean find = matcher2.find();// TODO check find
			duration = Integer.valueOf(matcher2.group(1));
		}

		// save cast
		int lastHour = utopiaService.getHourOfAge() + duration;
		Person person = personService.find(input.getUser(), true);
		if (person == null) {
			return new ErrorResult("Unrecognized player, register your province/nick");
		}

		// Create model
		SpellCast cast = new SpellCast();
		cast.setSpellId(spell.getId());
		cast.setLastHour(lastHour);
		cast.setCaster(person);
		cast.setTarget(person.getProvince());
		spellService.create(cast, true);

		return buildResponse(spell.getName(), person.getName(), duration);
	}

	@Override
	public boolean hasHelp() {
		return false;
	}

	@Override
	public List<String> getHelpBody() {
		return null;
	}

	@Override
	public String getSimpleHelp() {
		return null;
	}
}
