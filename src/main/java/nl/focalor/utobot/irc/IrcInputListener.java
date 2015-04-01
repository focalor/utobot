package nl.focalor.utobot.irc;

import nl.focalor.utobot.base.input.IInputListener;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class IrcInputListener extends ListenerAdapter<PircBotX> implements
		IIrcInputListener {
	private final IInputListener listener;

	public IrcInputListener(IInputListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		IResult result = listener.onMessage(event.getUser().getNick(),
				event.getMessage());

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