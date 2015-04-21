package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.base.model.service.IPersonService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class DeletePersonHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "delperson";

	@Autowired
	private IPersonService personService;

	public DeletePersonHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		if (StringUtils.isEmpty(event.getArgument())) {
			return new ErrorResult("Missing required parameter");
		}

		personService.deleteByNameIgnoreCase(event.getArgument());
		return new ReplyResult("Person deleted");
	}

	@Override
	public String getSimpleHelp() {
		return "Deletes the specified person. Use '!help delperson' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Deletes the specified person, including nicks, attacks and spells");
		helpBody.add("USAGE:");
		helpBody.add("!delprov <NAME>");
		helpBody.add("e.g.:");
		helpBody.add("!delprov Sephi");
		return helpBody;
	}
}