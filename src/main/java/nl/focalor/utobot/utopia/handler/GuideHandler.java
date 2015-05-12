package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.handler.AbstractGenericCommandHandler;
import nl.focalor.utobot.base.input.handler.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */

@Component
public class GuideHandler extends AbstractGenericCommandHandler {
	public static final String COMMAND_NAME = "guide";

	@Autowired
	private ICommandHandler linkHandler;

	public GuideHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		event.setArgument("guide " + event.getArgument());
		return linkHandler.handleCommand(event);
	}

	@Override
	public String getSimpleHelp() {
		return "Shows the requested guide link.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Shows the guide requested link.");
		helpBody.add("USAGE:");
		helpBody.add("!guide <id>");
		helpBody.add("e.g.:");
		helpBody.add("!guide dragon");
		return helpBody;
	}
}