package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import nl.focalor.utobot.base.input.handler.IGenericInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IGenericRegexHandler;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.handler.spells.SelfSpellHandler;
import nl.focalor.utobot.utopia.handler.spells.TargetedSpellHandler;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.service.IProvinceService;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddSpellHandlerFactory implements IGenericInputHandlerFactory {
	private static final String NAME = "rawspell";
	private final List<IGenericRegexHandler> handlers = new ArrayList<>();

	@Autowired
	private IPersonService personService;
	@Autowired
	private IProvinceService provinceService;
	@Autowired
	private IUtopiaService utopiaService;
	@Autowired
	private ISpellService spellService;

	@PostConstruct
	public void init() {
		for (Spell spell : spellService.getKnownSpells()) {
			if (!StringUtils.isEmpty(spell.getSelfSyntax())) {
				handlers.add(new SelfSpellHandler(utopiaService, personService, spellService, spell));
			}

			if (!StringUtils.isEmpty(spell.getTargetSyntax())) {
				handlers.add(new TargetedSpellHandler(utopiaService, personService, provinceService, spellService,
						spell));
			}
		}
	}

	@Override
	public List<IGenericRegexHandler> getRegexHandlers() {
		return handlers;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getSimpleHelp() {
		return "Adds an spell based on raw input. Use '!help rawspell' for more info";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Adds an spell based on raw input");
		helpBody.add("e.g.:");
		helpBody.add("Our realm is now under a sphere of minor protection for 16 days!");
		helpBody.add("Our army has been inspired to train harder. We expect maintenance costs to be reduced for 10 days!");
		return helpBody;
	}
}