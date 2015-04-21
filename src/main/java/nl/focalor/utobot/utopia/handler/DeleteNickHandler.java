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
public class DeleteNickHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "delnick";

	@Autowired
	private IPersonService personService;

	public DeleteNickHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		if (StringUtils.isEmpty(event.getArgument())) {
			return new ErrorResult("Missing required parameter");
		}

		personService.deleteByNickIgnoreCase(event.getArgument());
		return new ReplyResult("Nickname deleted");
	}

	@Override
	public String getSimpleHelp() {
		return "Deletes the specified nick. Use '!help delnick' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Deletes the specified nickname");
		helpBody.add("USAGE:");
		helpBody.add("!delnick <NICKNAME>");
		helpBody.add("e.g.:");
		helpBody.add("!delnick DarkSephi");
		return helpBody;
	}
}
