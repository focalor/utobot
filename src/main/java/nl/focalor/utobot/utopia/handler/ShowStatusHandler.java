package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractGenericCommandHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.entity.Attack;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.IAttackService;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class ShowStatusHandler extends AbstractGenericCommandHandler {
	public static final String COMMAND = "status";
	@Autowired
	private IPersonService personService;
	@Autowired
	private IAttackService attackService;
	@Autowired
	private ISpellService spellService;
	@Autowired
	private IUtopiaService utopiaService;

	public ShowStatusHandler() {
		super(COMMAND);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		String name = event.getArgument() == null ? event.getUser() : event.getArgument();

		List<Attack> attacks;
		List<SpellCast> spellCasts;
		Person person = personService.find(name, false);
		if (person == null) {
			return new ErrorResult("Person " + name + " not found");
		}
		attacks = attackService.findByPerson(person);
		spellCasts = spellService.findByCasterAndTarget(person, person.getProvince());

		List<String> messages = new ArrayList<>();
		messages.add("Status for " + person.getName());
		messages.add("Armies out:");
		//@formatter:off
		messages.addAll(attacks.stream()
					.sorted((left, right) -> left.getReturnDate().compareTo(right.getReturnDate()))
					.map(attack -> attack.toString(false))
					.collect(Collectors.toList()));
		//@formatter:on
		messages.add("Active spells:");
		messages.addAll(spellCasts.stream().map(this::toMessage).collect(Collectors.toList()));
		return new MultiReplyResult(messages);
	}

	private String toMessage(SpellCast cast) {
		StringBuilder builder = new StringBuilder();
		builder.append(cast.getSpellId());
		builder.append(" ends in ");

		int hoursLeft = cast.getLastHour() - utopiaService.getHourOfAge() + 1;
		builder.append(hoursLeft);
		builder.append(" day");
		if (hoursLeft != 1) {
			builder.append('s');
		}
		return builder.toString();
	}

	@Override
	public String getSimpleHelp() {
		return "Shows the status of the entire KD or a single province. Use '!help status' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("USAGE:");
		helpBody.add("!status [<province>]");
		helpBody.add("e.g.:");
		helpBody.add("!status Sephi");
		return helpBody;
	}
}
