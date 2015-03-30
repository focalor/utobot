package nl.focalor.utobot.hipchat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.hipchat.model.Notification;
import nl.focalor.utobot.hipchat.service.IHipchatService;

import org.apache.commons.lang3.StringUtils;

public class HipchatInputListener implements IHipchatInputListener {
	private final Map<String, ICommandHandler> handlers = new HashMap<>();
	private final List<IRegexHandler> regexHandlers;
	private final IHipchatService hipchatService;

	public HipchatInputListener(List<IRegexHandler> regexHandlers,
			IHipchatService hipchatService) {
		super();
		this.regexHandlers = regexHandlers;
		this.hipchatService = hipchatService;
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
	public void onRoomMessage(String room, String message) {
		if (message.length() == 0) {
			return;
		}
		IResult result = onMessage(message);

		// handle result
		if (result == null) {
			// Ignore unknown commands
		} else if (result instanceof ReplyResult) {
			Notification msg = new Notification();
			msg.setMessage(((ReplyResult) result).getMessage());

			hipchatService.sendMessage(room, msg);
		} else if (result instanceof MultiReplyResult) {
			MultiReplyResult res = (MultiReplyResult) result;
			Notification msg = new Notification();
			msg.setMessage(StringUtils.join(res.getMessages(), "\r\n"));

			hipchatService.sendMessage(room, msg);
		} else {
			throw new UnsupportedOperationException(
					"Don't know how to handle result of type "
							+ result.getClass().getName());
		}
	}
}
