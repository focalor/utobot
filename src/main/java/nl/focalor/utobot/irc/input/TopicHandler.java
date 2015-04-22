package nl.focalor.utobot.irc.input;

import java.util.Arrays;
import java.util.List;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.irc.bot.UtoPircBotX;
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
	public IResult handleCommand(MessageEvent<UtoPircBotX> event) {
		return new ReplyResult(event.getChannel().getTopic());
	}

	@Override
	public String getName() {
		return COMMAND_NAME;
	}

	@Override
	public boolean hasHelp() {
		return false;
	}

	@Override
	public List<String> getHelpBody() {
		return null;
	}

	@Override
	public String getSimpleHelp() {
		return null;
	}
}
