package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.utopia.model.entity.War;
import nl.focalor.utobot.utopia.service.IUtopiaService;
import nl.focalor.utobot.utopia.service.IWarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by luigibanzato on 12/04/2015.
 */
@Component
public class WarStartHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "startwar";
	public static final String[] ALTERNATE_NAMES = { "warstart" };

	@Autowired
	private IWarService warService;

	@Autowired
	private IUtopiaService utopiaService;

	public WarStartHandler() {
		super(COMMAND_NAME, ALTERNATE_NAMES);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		String startDate = event.getArgument() == null ? null : event.getArgument();
		War newWar;

		if (startDate == null) {
			newWar = warService.startWar();
		} else {
			War war = new War();

			Date warStartDate = utopiaService.getRealDateFromUtopianDateString(startDate);
			war.setStartDate(warStartDate);
			warService.addWar(war);

			newWar = warService.getCurrentWar();
		}

		StringBuilder reply = new StringBuilder();
		reply.append("New War started. War Id: ");
		reply.append(newWar.getId());
		reply.append(". Start Date: ");
		reply.append(newWar.getStartDate());
		reply.append(".");

		return new ReplyResult(reply.toString());
	}

	@Override
	public String getSimpleHelp() {
		return "Registers the start of a KD war. Use '!help war' for more info";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Registers the start of a KD war.");
		helpBody.add("USAGE:");
		helpBody.add("!startwar [<date>]");
		helpBody.add("e.g.:");
		helpBody.add("!startwar March 3, YR2");
		return helpBody;
	}
}
