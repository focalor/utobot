package nl.focalor.utobot.hipchat;

import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.hipchat.model.Message;
import nl.focalor.utobot.hipchat.model.User;
import nl.focalor.utobot.hipchat.service.IHipchatService;

public class HipchatInputListener implements IHipchatInputListener {
	private final IInputListener listener;
	private final IHipchatService hipchatService;

	public HipchatInputListener(IInputListener listener, IHipchatService hipchatService) {
		super();
		this.listener = listener;
		this.hipchatService = hipchatService;
	}

	@Override
	public void onRoomMessage(String room, User user, String message) {
		String name = user.getName();
		// Check for full name, in which case only use first part
		int index = name.indexOf(' ');
		if (index >= 0) {
			name = name.substring(0, index);
		}
		IResult result = listener.onMessage(name, message);

		// handle result
		if (result == null) {
			// Ignore unknown commands
		} else if (result instanceof ReplyResult) {
			send(user.getId(), ((ReplyResult) result).getMessage());
		} else if (result instanceof MultiReplyResult) {
			MultiReplyResult res = (MultiReplyResult) result;
			for (String msg : res.getMessages()) {
				send(user.getId(), msg);
			}
		} else {
			throw new UnsupportedOperationException("Don't know how to handle result of type "
					+ result.getClass().getName());
		}
	}

	private void send(int userId, String msg) {
		Message toSend = new Message();
		toSend.setMessage(msg);
		toSend.setNotify(true);
		toSend.setMessageFormat("text");

		hipchatService.sendPrivateMessage(userId, toSend);
	}
}
