package nl.focalor.utobot.irc.input;

import java.util.Arrays;
import java.util.List;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class TopicHandler implements IIrcInputHandler {
	private static final String COMMAND_NAME = "topic";

	@Override
	public List<String> getCommandNames() {
		return Arrays.asList(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(MessageEvent<PircBotX> event) {
		return new ReplyResult(event.getChannel().getTopic());
	}

	@Override
	public String getName() {
		return COMMAND_NAME;
	}
}
