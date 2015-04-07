package nl.focalor.utobot.hipchat.service;

import nl.focalor.utobot.hipchat.model.Notification;
import nl.focalor.utobot.hipchat.model.Webhook;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HipchatService implements IHipchatService {
	private final RestTemplate template;
	private static final String AUTH_TOKEN = "okNe4C0SI4VJxg7xnLXz235HCi4OSMdqctPycz0W";

	public HipchatService() {
		template = new RestTemplate();
	}

	@Override
	public void registerHook(String room, Webhook webhook) {
		StringBuilder url = new StringBuilder();
		url.append("https://www.hipchat.com/v2/room/");
		url.append(room);
		url.append("/webhook?auth_token=");
		url.append(AUTH_TOKEN);

		template.postForLocation(url.toString(), webhook);
	}

	@Override
	public void sendMessage(String room, Notification message) {
		StringBuilder url = new StringBuilder();
		url.append("https://www.hipchat.com/v2/room/");
		url.append(room);
		url.append("/notification?auth_token=");
		url.append(AUTH_TOKEN);

		template.postForLocation(url.toString(), message);
	}

	@Override
	public void broadcastMessage(String message) {
		Notification not = new Notification();
		not.setMessage(message);
		sendMessage("Warcry", not);
	}
}
