package nl.focalor.utobot.irc.input;

import java.util.List;

import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.NoReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.OrdersHandler;
import nl.focalor.utobot.base.input.listener.AbstractInputListener;
import nl.focalor.utobot.irc.bot.UtoPircBotX;
import nl.focalor.utobot.utopia.handler.ShowStatusHandler;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IrcInputListener extends AbstractInputListener implements IIrcInputListener {
	private static final Logger LOG = LoggerFactory.getLogger(IrcInputListener.class);

	@Autowired
	private ShowStatusHandler showStatusHandler;
	@Autowired
	private OrdersHandler ordersHandler;

	@Override
	public void onMessage(MessageEvent<UtoPircBotX> event) throws Exception {
		User user = event.getUser();
		try {
			String username = user.getNick();
			String input = event.getMessage();
			IResult result = super.onMessage(event.getChannel().getName(), username, input);

			handleResult(user, result);
		} catch (Exception ex) {
			handleError(user, ex);
		}
	}

	@Override
	public void onJoin(JoinEvent<UtoPircBotX> event) {
		User user = event.getUser();
		try {
			handleResult(user, showStatusHandler.getStatus(user.getNick()));
			handleResult(user, ordersHandler.handleCommand(null));
		} catch (Exception ex) {
			handleError(user, ex);
		}
	}

	private void handleResult(User user, IResult result) {
		if (result == NoReplyResult.NO_REPLY) {
		} else if (result instanceof ErrorResult) {
			handleReply(user, (ErrorResult) result);
		} else if (result instanceof ReplyResult) {
			handleReply(user, (ReplyResult) result);
		} else if (result instanceof MultiReplyResult) {
			handleReply(user, (MultiReplyResult) result);
		} else {
			throw new UnsupportedOperationException("Don't know how to handle result of type "
					+ result.getClass().getName());
		}
	}

	private void handleReply(User user, MultiReplyResult reply) {
		for (String msg : reply.getMessages()) {
			handleReply(user, msg);
		}
	}

	private void handleError(User user, Exception ex) {
		LOG.error("Unexpected exception", ex);
		if (StringUtils.isEmpty(ex.getMessage())) {
			handleReply(user, Colors.RED + "Error: Unexpected exception, contact bot admin");
		} else {
			handleReply(user, Colors.RED + "Error: " + ex.getMessage());
		}
	}

	private void handleReply(User user, ErrorResult reply) {
		handleReply(user, Colors.RED + reply.getMessage());
	}

	private void handleReply(User user, ReplyResult reply) {
		handleReply(user, reply.getMessage());
	}

	private void handleReply(User user, String reply) {
		user.send().notice(reply);
	}

	// Set IRC specific handlers

	@Autowired
	public void setIrcCommandHandlers(List<IIrcCommandHandler> ircCommandHandlers) {
		addCommandHandlers(ircCommandHandlers);
	}
}