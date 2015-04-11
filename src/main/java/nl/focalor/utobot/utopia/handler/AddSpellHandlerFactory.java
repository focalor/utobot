package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractRegexHandler;
import nl.focalor.utobot.base.input.handler.IInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.SpellType;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Component
public class AddSpellHandlerFactory implements IInputHandlerFactory {
	private final List<IRegexHandler> handlers = new ArrayList<>();
	@Autowired
	private IPersonService personService;
	@Autowired
	private IUtopiaService utopiaService;
	@Autowired
	private ISpellService spellService;

	@PostConstruct
	public void init() {
		for (SpellType spellType : spellService.getKnownSpellTypes()) {
			handlers.add(new SpellHandler(spellType));
		}
	}

	@Override
	public List<IRegexHandler> getRegexHandlers() {
		return handlers;
	}

	private class SpellHandler extends AbstractRegexHandler {
		private final SpellType spell;

		public SpellHandler(SpellType spell) {
			super(spell.getSyntax());
			this.spell = spell;
		}

		@Override
		public IResult handleInput(IInput input) {
			Matcher matcher = getMatcher(input);
			if (!matcher.find()) {
				throw new IllegalStateException("Input does not match expected input");
			}

			// Gather data
			Integer duration = Integer.valueOf(matcher.group(1));
			int lastHour = utopiaService.getHourOfAge() + duration;
			Person person = personService.find(input.getSource(), true);

			// Create model
			SpellCast cast = new SpellCast();
			cast.setSpellId(spell.getId());
			cast.setLastHour(lastHour);
			cast.setPerson(person);
			spellService.create(cast, true);

			// Create response
			StringBuilder builder = new StringBuilder();
			builder.append("Spell added for ");
			builder.append(input.getSource());
			builder.append(" for ");
			builder.append(duration);
			builder.append(" hours");
			return new ReplyResult(builder.toString());
		}
	}
}