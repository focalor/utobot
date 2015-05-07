package nl.focalor.utobot.hipchat.service;

import java.util.List;
import nl.focalor.utobot.base.service.ICommunicationService;
import nl.focalor.utobot.hipchat.model.Message;
import nl.focalor.utobot.hipchat.model.Notification;
import nl.focalor.utobot.hipchat.model.Room;
import nl.focalor.utobot.hipchat.model.Webhook;
import nl.focalor.utobot.hipchat.model.Webhooks;

public interface IHipchatService extends ICommunicationService {
	public Room getRoom(String room);

	public void registerHook(String room, Webhook webhook);

	public void sendMessage(String room, Notification message);

	public void sendPrivateMessage(int userId, Message message);

	public void updateWebhooks(String roomId);

	public void createWebhook(String roomId, Webhook webhook);

	public void deleteWebhook(String roomId, Long webhookId);

	public Webhooks getWebhooks(String roomId);

	public List<Webhook> getUtobotWebhooks(String roomId);
}
