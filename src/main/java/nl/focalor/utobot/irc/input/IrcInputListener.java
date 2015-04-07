package nl.focalor.utobot.irc.input;

import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.Input;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.listener.IInputListener;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IrcInputListener extends ListenerAdapter<PircBotX> implements IIrcInputListener {
	private final IInputListener listener;
	private List<IIrcInputHandler> ircHandlers;

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
		IResult result = handle(event);

		if (result == null) {
			// Ignore unknown commands
		} else if (result instanceof ReplyResult) {
			handleReply(event, (ReplyResult) result);
		} else if (result instanceof MultiReplyResult) {
			handleReply(event, (MultiReplyResult) result);
		} else {
			throw new UnsupportedOperationException("Don't know how to handle result of type "
					+ result.getClass().getName());
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

	private void handleReply(MessageEvent<PircBotX> event, ReplyResult reply) {
		event.getChannel().send().message(reply.getMessage());
	}

	private void handleReply(MessageEvent<PircBotX> event, MultiReplyResult reply) {
		String msg = StringUtils.join(reply.getMessages(), "\r\n");
		event.getChannel().send().message(msg);
	}
}