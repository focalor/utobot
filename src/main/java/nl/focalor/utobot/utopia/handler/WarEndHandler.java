package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.utopia.model.entity.War;
import nl.focalor.utobot.utopia.service.IWarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by luigibanzato on 12/04/2015.
 */
@Component
public class WarEndHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "endwar";
	public static final String[] ALTERNATE_NAMES = {"endwar"};

	@Autowired
	private IWarService warService;

	public WarEndHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		War currentWar = warService.getCurrentWar();
		Long id = currentWar.getId();
		warService.endWar();

		StringBuilder reply = new StringBuilder();
		reply.append("War Ended. War Id: ");
		reply.append(id);
		reply.append(".");

		return new ReplyResult(reply.toString());
	}
}
