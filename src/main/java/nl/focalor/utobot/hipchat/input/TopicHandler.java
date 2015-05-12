package nl.focalor.utobot.hipchat.input;

import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.NoReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.hipchat.service.IHipchatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component("HipchatTopicHandler")
public class TopicHandler implements IHipchatCommandHandler {
	private static final String COMMAND_NAME = "topic";

	@Autowired
	private IBotService botService;
	@Autowired
	private IHipchatService hipchatService;

	@Override
	public String getName() {
		return COMMAND_NAME;
	}

	@Override
	public IResult handleCommand(CommandInput command) {
		if (StringUtils.isNotEmpty(command.getArgument())) {
			botService.setTopic(command.getArgument());
			return NoReplyResult.NO_REPLY;
		} else {
			return new ReplyResult(hipchatService.getRoom(command.getRoom()).getTopic());
		}
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