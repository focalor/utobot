package nl.focalor.utobot.irc.input;

import java.util.List;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.NoReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.listener.AbstractInputListener;
import nl.focalor.utobot.irc.bot.UtoPircBotX;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IrcInputListener extends AbstractInputListener implements IIrcInputListener {
	private static final Logger LOG = LoggerFactory.getLogger(IrcInputListener.class);

	@Override
	public void onMessage(MessageEvent<UtoPircBotX> event) throws Exception {
		try {
			String user = event.getUser().getNick();
			String input = event.getMessage();
			IResult result = super.onMessage(event.getChannel().getName(), user, input);

			if (result == NoReplyResult.NO_REPLY) {
			} else if (result instanceof ErrorResult) {
				handleReply(event, (ErrorResult) result);
			} else if (result instanceof ReplyResult) {
				handleReply(event, (ReplyResult) result);
			} else if (result instanceof MultiReplyResult) {
				handleReply(event, (MultiReplyResult) result);
			} else {
				throw new UnsupportedOperationException("Don't know how to handle result of type "
						+ result.getClass().getName());
			}
		} catch (Exception ex) {
			handleError(event, ex);
		}
	}

	private void handleReply(MessageEvent<UtoPircBotX> event, MultiReplyResult reply) {
		for (String msg : reply.getMessages()) {
			handleReply(event, msg);
		}
	}

	private void handleError(MessageEvent<UtoPircBotX> event, Exception ex) {
		LOG.error("Unexpected exception", ex);
		if (StringUtils.isEmpty(ex.getMessage())) {
			handleReply(event, Colors.RED + "Error: Unexpected exception, contact bot admin");
		} else {
			handleReply(event, Colors.RED + "Error: " + ex.getMessage());
		}
	}

	private void handleReply(MessageEvent<UtoPircBotX> event, ErrorResult reply) {
		handleReply(event, Colors.RED + reply.getMessage());
	}

	private void handleReply(MessageEvent<UtoPircBotX> event, ReplyResult reply) {
		handleReply(event, reply.getMessage());
	}

	private void handleReply(MessageEvent<UtoPircBotX> event, String reply) {
		event.getUser().send().notice(reply);
	}

	// Set IRC specific handlers

	@Autowired
	public void setIrcCommandHandlers(List<IIrcCommandHandler> ircCommandHandlers) {
		addCommandHandlers(ircCommandHandlers);
	}
}