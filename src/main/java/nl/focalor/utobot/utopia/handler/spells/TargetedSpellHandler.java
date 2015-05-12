package nl.focalor.utobot.utopia.handler.spells;

import java.util.List;
import java.util.regex.Matcher;

import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.IProvinceService;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

/**
 * @author focalor
 */
public class TargetedSpellHandler extends AbstractSpellHandler {
	private final IUtopiaService utopiaService;
	private final IPersonService personService;
	private final IProvinceService provinceService;
	private final ISpellService spellService;
	private final Spell spell;

	public TargetedSpellHandler(IUtopiaService utopiaService, IPersonService personService,
			IProvinceService provinceService, ISpellService spellService, Spell spell) {
		super(spell.getName(), spell.getTargetSyntax());
		this.utopiaService = utopiaService;
		this.personService = personService;
		this.provinceService = provinceService;
		this.spellService = spellService;
		this.spell = spell;
	}

	@Override
	public IResult handleInput(Matcher matcher, IInput input) {
		String province = matcher.group("province");
		int duration = Integer.valueOf(matcher.group("duration"));
		int lastHour = utopiaService.getHourOfAge() + duration;

		Province newProv = Province.createFor(province);
		Province dbProv = provinceService.findByNameAndIslandAndKingdom(newProv.getName(), newProv.getIsland(),
				newProv.getKingdom());
		if (dbProv == null) {
			dbProv = provinceService.create(newProv);
		}

		SpellCast cast = new SpellCast();
		cast.setLastHour(lastHour);
		cast.setTarget(dbProv);
		cast.setSpellId(spell.getId());

		Person person = personService.find(input.getUser(), true);
		cast.setCaster(person);

		// TODO clean up province after cast expired?
		spellService.create(cast, true);

		return buildResponse(spell.getName(), province, duration);
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