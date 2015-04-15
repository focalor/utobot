package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.utopia.model.entity.War;
import nl.focalor.utobot.utopia.service.IUtopiaService;
import nl.focalor.utobot.utopia.service.IWarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luigibanzato on 12/04/2015.
 */
@Component
public class WarEndHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "endwar";
	public static final String[] ALTERNATE_NAMES = {"warend"};

	@Autowired
	private IWarService warService;

	@Autowired
	private IUtopiaService utopiaService;

	public WarEndHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		String endDate = event.getArgument() == null ? null : event.getArgument();

		War currentWar = warService.getCurrentWar();
		Long id = currentWar.getId();
		if(endDate == null) {
			warService.endWar();
		}
		else {
			currentWar.setEndDate(utopiaService.getRealDateFromUtopianDateString(endDate));
			warService.updateDate(currentWar);
		}

		StringBuilder reply = new StringBuilder();
		reply.append("War Ended. War Id: ");
		reply.append(id);
		reply.append(".");

		return new ReplyResult(reply.toString());
	}

	@Override
	public boolean hasHelp() {
		return true;
	}

	@Override
	public String getSimpleHelp() {
		return "Ends the current war. Use '!help endwar' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Ends the current war.");
		helpBody.add("USAGE:");
		helpBody.add("!endwar [<date>]");
		helpBody.add("e.g.:");
		helpBody.add("!endwar March 3, YR2");
		return helpBody;
	}
}
