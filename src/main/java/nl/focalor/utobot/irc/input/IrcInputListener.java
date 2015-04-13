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
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IrcInputListener extends ListenerAdapter<PircBotX> implements IIrcInputListener {
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
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
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
			LOG.error("Unexpected exception", ex);
			handleError(event, ex);
		}
	}

	private IResult handle(MessageEvent<PircBotX> event) {
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

	private void handleReply(MessageEvent<PircBotX> event, MultiReplyResult reply) {
		for (String msg : reply.getMessages()) {
			handleReply(event, msg);
		}
	}

	private void handleError(MessageEvent<PircBotX> event, Exception ex) {
		handleReply(event, Colors.RED + ex.getMessage());
	}

	private void handleReply(MessageEvent<PircBotX> event, ErrorResult reply) {
		handleReply(event, Colors.RED + reply.getMessage());
	}

	private void handleReply(MessageEvent<PircBotX> event, ReplyResult reply) {
		handleReply(event, reply.getMessage());
	}

	private void handleReply(MessageEvent<PircBotX> event, String reply) {
		event.getUser().send().notice(reply);
	}

}