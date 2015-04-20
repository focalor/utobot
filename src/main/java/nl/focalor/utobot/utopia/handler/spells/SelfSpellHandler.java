package nl.focalor.utobot.utopia.handler.spells;

import java.util.regex.Matcher;
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
		// Gather data
		Integer duration = Integer.valueOf(matcher.group(1));
		int lastHour = utopiaService.getHourOfAge() + duration;
		Person person = personService.find(input.getSource(), true);
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
}
