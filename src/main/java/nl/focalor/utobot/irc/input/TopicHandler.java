package nl.focalor.utobot.irc.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.NoReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.irc.bot.UtoPircBotX;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component("IrcTopicHandler")
public class TopicHandler implements IIrcCommandHandler {
	private static final String COMMAND_NAME = "topic";

	@Autowired
	private IBotService botService;

	@Override
	public List<String> getCommandNames() {
		return Arrays.asList(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput input) {
		if (StringUtils.isNotEmpty(input.getArgument())) {
			botService.setTopic(input.getArgument());
			return NoReplyResult.NO_REPLY;
		} else {
			MessageEvent<UtoPircBotX> param = input.getParameter(InputParameter.MESSAGE_EVENT.getName());
			return new ReplyResult(param.getChannel().getTopic());
		}
	}

	@Override
	public String getName() {
		return COMMAND_NAME;
	}

	@Override
	public boolean hasHelp() {
		return true;
	}

	@Override
	public String getSimpleHelp() {
		return "Shows or sets the topic. Use '!help topic' for more info";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Shows or sets the topic");
		helpBody.add("USAGE:");
		helpBody.add("!topic");
		helpBody.add("!topic <NEW TOPIC>");
		helpBody.add("e.g.:");
		helpBody.add("!topic PK Sephiroth");
		return helpBody;
	}
}
