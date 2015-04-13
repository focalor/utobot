package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.utopia.service.IUtopiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DateHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "date";
	public static final String[] ALTERNATE_NAMES = {"time"};
	@Autowired
	private IUtopiaService utopiaService;

	public DateHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		return new ReplyResult(utopiaService.getUtopiaDate().toString());
	}

	@Override
	public String getSimpleHelp() {
		return "Shows the current utopia date";
	}
}
