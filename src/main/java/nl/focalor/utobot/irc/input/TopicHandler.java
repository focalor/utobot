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
	private final List<String> commandNames = Arrays.asList("topic");

	@Override
	public List<String> getCommandNames() {
		return commandNames;
	}

	@Override
	public IResult handleCommand(MessageEvent<PircBotX> event) {
		return new ReplyResult(event.getChannel().getTopic());
	}
}
