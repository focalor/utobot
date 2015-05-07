package nl.focalor.utobot.hipchat.input;

import java.util.List;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.listener.AbstractInputListener;
import nl.focalor.utobot.hipchat.model.Message;
import nl.focalor.utobot.hipchat.service.IHipchatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HipchatInputListener extends AbstractInputListener implements IHipchatInputListener {
	private static final Logger LOG = LoggerFactory.getLogger(HipchatInputListener.class);

	private final IHipchatService hipchatService;

	@Autowired
	public HipchatInputListener(IHipchatService hipchatService) {
		super();
		this.hipchatService = hipchatService;
	}

	@Override
	public void onRoomMessage(HipchatMessageEvent event) {
		try {
			String name = event.getUser().getName();
			// Check for full name, in which case only use first part
			int index = name.indexOf(' ');
			if (index >= 0) {
				name = name.substring(0, index);
			}
			IResult result = super.onMessage(event.getRoom(), name, event.getMessage());

			// handle result
			if (result == null) {
				// Ignore unknown commands

			} else if (result instanceof ErrorResult) {
				send(event.getUser().getId(), ((ErrorResult) result).getMessage());
			} else if (result instanceof ReplyResult) {
				send(event.getUser().getId(), ((ReplyResult) result).getMessage());
			} else if (result instanceof MultiReplyResult) {
				MultiReplyResult res = (MultiReplyResult) result;
				for (String msg : res.getMessages()) {
					send(event.getUser().getId(), msg);
				}
			} else {
				throw new UnsupportedOperationException("Don't know how to handle result of type "
						+ result.getClass().getName());
			}
		} catch (Exception ex) {
			handleError(event.getUser().getId(), ex);
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

	// Set Hipchat specific handlers

	@Autowired
	public void setHipchatCommandHandlers(List<IHipchatCommandHandler> hipchatCommandHandlers) {
		addCommandHandlers(hipchatCommandHandlers);
	}
}
