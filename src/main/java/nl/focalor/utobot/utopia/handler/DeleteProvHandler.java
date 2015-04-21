package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.utopia.service.IProvinceService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class DeleteProvHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "delprov";

	@Autowired
	private IProvinceService provinceService;

	public DeleteProvHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		if (StringUtils.isEmpty(event.getArgument())) {
			return new ErrorResult("Missing required parameter");
		}

		provinceService.deleteByNameIgnoreCase(event.getArgument());
		return new ReplyResult("province deleted");
	}

	@Override
	public String getSimpleHelp() {
		return "Deletes the specified province. Use '!help delprov' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Deletes the specified province, including attacks and spells");
		helpBody.add("USAGE:");
		helpBody.add("!delprov <PROVINCE>");
		helpBody.add("e.g.:");
		helpBody.add("!delprov Naughty Nisall");
		return helpBody;
	}
}