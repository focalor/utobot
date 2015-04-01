package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javax.annotation.PostConstruct;

import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractRegexHandler;
import nl.focalor.utobot.base.input.handler.IInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.model.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellCastService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddSpellHandlerFactory implements IInputHandlerFactory {
	private final List<IRegexHandler> handlers = new ArrayList<>();
	@Autowired
	private IUtopiaService utopiaService;
	@Autowired
	private ISpellCastService spellCastService;

	@PostConstruct
	public void init() {
		for (Spell spell : spellCastService.getKnownSpells()) {
			handlers.add(new AbstractRegexHandler(spell.getSyntax()) {

				@Override
				public IResult handleInput(IInput input) {
					Matcher matcher = getMatcher(input);
					if (!matcher.find()) {
						throw new IllegalStateException(
								"Input does not match expected input");
					}
					Integer duration = Integer.valueOf(matcher.group(1));
					int lastHour = utopiaService.getHourOfAge() + duration;

					SpellCast cast = new SpellCast();
					cast.setSpellId(spell.getId());
					cast.setLastHour(lastHour);
					spellCastService.create(cast, true);

					return new ReplyResult("Added spell " + spell.getId());
				}
			});
		}
	}

	@Override
	public List<IRegexHandler> getRegexHandlers() {
		return handlers;
	}
}
