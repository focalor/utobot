package nl.focalor.utobot.hipchat;

import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.hipchat.model.Message;
import nl.focalor.utobot.hipchat.model.User;
import nl.focalor.utobot.hipchat.service.IHipchatService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HipchatInputListener implements IHipchatInputListener {
	private static final Logger LOG = LoggerFactory.getLogger(HipchatInputListener.class);

	private final IInputListener listener;
	private final IHipchatService hipchatService;

	public HipchatInputListener(IInputListener listener, IHipchatService hipchatService) {
		super();
		this.listener = listener;
		this.hipchatService = hipchatService;
	}

	@Override
	public void onRoomMessage(String room, User user, String message) {
		try {
			String name = user.getName();
			// Check for full name, in which case only use first part
			int index = name.indexOf(' ');
			if (index >= 0) {
				name = name.substring(0, index);
			}

			String[] lines = message.split("[\r\n]");
			for (String line : lines) {
				IResult result = listener.onMessage(name, line);

				// handle result
				if (result == null) {
					// Ignore unknown commands

				} else if (result instanceof ErrorResult) {
					send(user.getId(), ((ErrorResult) result).getMessage());
				} else if (result instanceof ReplyResult) {
					send(user.getId(), ((ReplyResult) result).getMessage());
				} else if (result instanceof MultiReplyResult) {
					MultiReplyResult res = (MultiReplyResult) result;
					send(user.getId(), StringUtils.join(res.getMessages(), "\n"));
				} else {
					throw new UnsupportedOperationException("Don't know how to handle result of type "
							+ result.getClass().getName());
				}
			}
		} catch (Exception ex) {
			handleError(user.getId(), ex);
		}
	}

	private void send(int userId, String msg) {
		Message toSend = new Message();
		toSend.setMessage(msg);
		toSend.setNotify(true);
		toSend.setMessageFormat("text");

		hipchatService.sendPrivateMessage(userId, toSend);
	}

	private void handleError(int userId, Exception ex) {
		LOG.error("Unexpected exception", ex);
		if (StringUtils.isEmpty(ex.getMessage())) {
			send(userId, "Error:  Unexpected exception, contact bot admin");
		} else {
			send(userId, "Error: " + ex.getMessage());
		}
	}
}
