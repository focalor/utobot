package nl.focalor.utobot.irc.input;

import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.Input;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.irc.bot.UtoPircBotX;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IrcInputListener extends ListenerAdapter<UtoPircBotX> implements IIrcInputListener {
	private static final Logger LOG = LoggerFactory.getLogger(IrcInputListener.class);

	private final IInputListener listener;
	private List<IIrcInputHandler> ircHandlers = new ArrayList<>(0);

	@Autowired
	public IrcInputListener(IInputListener listener) {
		super();
		this.listener = listener;
	}

	@Autowired(required = false)
	public void setIrcHandlers(List<IIrcInputHandler> ircHandlers) {
		this.ircHandlers = ircHandlers;
	}

	@Override
	public void onMessage(MessageEvent<UtoPircBotX> event) throws Exception {
		try {
			IResult result = handle(event);

			if (result == null) {
				// Ignore unknown commands
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

	private IResult handle(MessageEvent<UtoPircBotX> event) {
		String source = event.getUser().getNick();
		String input = event.getMessage();

		if (StringUtils.isEmpty(input)) {
			return null;
		} else if (input.charAt(0) == CommandInput.COMMAND_PREFIX) {
			CommandInput cmd = CommandInput.createFor(source, input);

			// Check IRC handlers before generic handlers
			for (IIrcInputHandler handler : ircHandlers) {
				if (handler.getCommandNames().contains(cmd.getCommand())) {
					return handler.handleCommand(event);
				}
			}
			return listener.onCommand(cmd);
		} else {
			return listener.onNonCommand(new Input(source, input));
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

}