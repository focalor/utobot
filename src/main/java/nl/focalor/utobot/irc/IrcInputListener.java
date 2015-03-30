package nl.focalor.utobot.irc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IRegexHandler;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class IrcInputListener extends ListenerAdapter<PircBotX> implements
		IIrcInputListener {
	private final List<IRegexHandler> regexHandlers;
	private final Map<String, ICommandHandler> handlers = new HashMap<>();

	public IrcInputListener(List<IRegexHandler> regexHandlers) {
		super();
		this.regexHandlers = regexHandlers;
	}

	@Override
	public List<IRegexHandler> getRegexHandlers() {
		return regexHandlers;
	}

	@Override
	public ICommandHandler getCommandHandler(String name) {
		return handlers.get(name.toLowerCase());
	}

	@Override
	public void put(String name, ICommandHandler handler) {
		handlers.put(name.toLowerCase(), handler);
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		String message = event.getMessage();
		if (message.length() == 0) {
			return;
		}

		IResult result = onMessage(message);

		if (result == null) {
			// Ignore unknown commands
		} else if (result instanceof ReplyResult) {
			handleReply(event, (ReplyResult) result);
		} else if (result instanceof MultiReplyResult) {
			handleReply(event, (MultiReplyResult) result);
		} else {
			throw new UnsupportedOperationException(
					"Don't know how to handle result of type "
							+ result.getClass().getName());
		}
	}

	private void handleReply(MessageEvent<PircBotX> event, ReplyResult reply) {
		event.getChannel().send().message(reply.getMessage());
	}

	private void handleReply(MessageEvent<PircBotX> event,
			MultiReplyResult reply) {
		String msg = StringUtils.join(reply.getMessages(), "\r\n");
		event.getChannel().send().message(msg);
	}
}
