package nl.focalor.utobot.utopia.handler;

import java.util.ArrayList;
import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.util.MathUtil;

import org.springframework.stereotype.Component;

@Component
public class NwHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "nw";
	private static final double DELTA = 0.2;

	public NwHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput command) {
		double input = Double.parseDouble(command.getArgument());
		double min = MathUtil.round(input * (1 - DELTA), 2);
		double max = MathUtil.round(input * (1 + DELTA), 2);

		StringBuilder reply = new StringBuilder();
		reply.append("Networth interval: ");
		reply.append(min);
		reply.append(" - ");
		reply.append(max);

		return new ReplyResult(reply.toString());
	}

	@Override
	public String getSimpleHelp() {
		return "Shows NW in range of the given NW. Use '!help nw' for more info.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Shows NW in range of the given NW.");
		helpBody.add("USAGE:");
		helpBody.add("!nw <your NW>");
		helpBody.add("e.g.:");
		helpBody.add("!nw 450000");
		return helpBody;
	}
}
