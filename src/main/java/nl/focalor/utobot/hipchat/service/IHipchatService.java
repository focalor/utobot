package nl.focalor.utobot.hipchat.service;

import nl.focalor.utobot.base.service.IMessagingService;
import nl.focalor.utobot.hipchat.model.Message;
import nl.focalor.utobot.hipchat.model.Notification;
import nl.focalor.utobot.hipchat.model.Webhook;

public interface IHipchatService extends IMessagingService {
	public void registerHook(String room, Webhook webhook);

	public void sendMessage(String room, Notification message);

	public void sendPrivateMessage(int userId, Message message);
}
