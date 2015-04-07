package nl.focalor.utobot.hipchat;

import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.hipchat.model.Notification;
import nl.focalor.utobot.hipchat.service.IHipchatService;
import org.apache.commons.lang3.StringUtils;

public class HipchatInputListener implements IHipchatInputListener {
	private final IInputListener listener;
	private final IHipchatService hipchatService;

	public HipchatInputListener(IInputListener listener, IHipchatService hipchatService) {
		super();
		this.listener = listener;
		this.hipchatService = hipchatService;
	}

	@Override
	public void onRoomMessage(String room, String user, String message) {
		IResult result = listener.onMessage(user, message);

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
			throw new UnsupportedOperationException("Don't know how to handle result of type "
					+ result.getClass().getName());
		}
	}
}
