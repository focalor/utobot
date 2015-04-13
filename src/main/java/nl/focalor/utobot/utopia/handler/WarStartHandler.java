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
public class WarStartHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "startwar";
	public static final String[] ALTERNATE_NAMES = {"warstart"};

	@Autowired
	private IWarService warService;

	public WarStartHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		War newWar = warService.startWar();

		StringBuilder reply = new StringBuilder();
		reply.append("New War started. War Id: ");
		reply.append(newWar.getId());
		reply.append(". Start Date: ");
		reply.append(newWar.getStartDate());
		reply.append(".");

		return new ReplyResult(reply.toString());
	}
}
