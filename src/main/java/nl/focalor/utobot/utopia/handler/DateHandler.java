package nl.focalor.utobot.utopia.handler;

import java.util.Arrays;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.utopia.service.IUtopiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DateHandler extends AbstractCommandHandler {
	public static final List<String> COMMAND_NAMES = Arrays.asList("date", "time");
	private final IUtopiaService utopiaService;

	@Autowired
	public DateHandler(IUtopiaService utopiaService) {
		super(COMMAND_NAMES);
		this.utopiaService = utopiaService;
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		return new ReplyResult(utopiaService.getUtopiaDate().toString());
	}
}
