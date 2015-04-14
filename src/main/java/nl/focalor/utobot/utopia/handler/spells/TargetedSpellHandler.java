package nl.focalor.utobot.utopia.handler.spells;

import java.util.regex.Matcher;

import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

/**
 * @author focalor
 */
public class TargetedSpellHandler extends AbstractSpellHandler {
	private final IUtopiaService utopiaService;
	private final IPersonService personService;
	private final ISpellService spellService;
	private final Spell spell;

	public TargetedSpellHandler(IUtopiaService utopiaService, IPersonService personService, ISpellService spellService,
			Spell spell) {
		super(spell.getName(), spell.getTargetSyntax());
		this.utopiaService = utopiaService;
		this.personService = personService;
		this.spellService = spellService;
		this.spell = spell;
	}

	@Override
	public IResult handleInput(Matcher matcher, IInput input) {
		String province = matcher.group("province");
		int duration = Integer.valueOf(matcher.group("duration"));
		int lastHour = utopiaService.getHourOfAge() + duration;

		Province targetProvince = new Province();
		targetProvince.setName(province); // TODO split off kd coords

		SpellCast cast = new SpellCast();
		cast.setLastHour(lastHour);
		cast.setTarget(targetProvince);
		cast.setSpellId(spell.getId());

		Person person = personService.find(input.getSource(), true);
		if (person != null) {
			cast.setCaster(person.getProvince());
		}

		// TODO persist under right province
		// remember to possibly clean up provice after cast expired
		spellService.create(cast, false);

		return buildResponse(spell.getName(), province, duration);
	}
}