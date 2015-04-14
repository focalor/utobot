package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import nl.focalor.utobot.base.input.handler.IInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.handler.spells.SelfSpellHandler;
import nl.focalor.utobot.utopia.handler.spells.TargetedSpellHandler;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddSpellHandlerFactory implements IInputHandlerFactory {
	private static final String NAME = "spell";
	private final List<IRegexHandler> handlers = new ArrayList<>();

	@Autowired
	private IPersonService personService;
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
				handlers.add(new TargetedSpellHandler(utopiaService, personService, spellService, spell));
			}
		}
	}

	@Override
	public List<IRegexHandler> getRegexHandlers() {
		return handlers;
	}

	@Override
	public String getName() {
		return NAME;
	}
}