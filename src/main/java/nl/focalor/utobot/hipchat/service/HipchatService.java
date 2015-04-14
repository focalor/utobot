package nl.focalor.utobot.hipchat.service;

import nl.focalor.utobot.hipchat.model.Message;
import nl.focalor.utobot.hipchat.model.Notification;
import nl.focalor.utobot.hipchat.model.Webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HipchatService implements IHipchatService {
	private final RestTemplate template;
	@Autowired
	private ObjectMapper jsonMapper = new ObjectMapper();
	@Value("${hipchat.token}")
	private String authToken;

	public HipchatService() {
		template = new RestTemplate();
	}

	@Override
	public void registerHook(String room, Webhook webhook) {
		StringBuilder url = new StringBuilder();
		url.append("https://www.hipchat.com/v2/room/");
		url.append(room);
		url.append("/webhook?auth_token=");
		url.append(authToken);

		template.postForLocation(url.toString(), webhook);
	}

	@Override
	public void sendMessage(String room, Notification message) {
		StringBuilder url = new StringBuilder();
		url.append("https://www.hipchat.com/v2/room/");
		url.append(room);
		url.append("/notification?auth_token=");
		url.append(authToken);

		template.postForLocation(url.toString(), message);
	}

	@Override
	public void sendPrivateMessage(int userId, Message message) {
		StringBuilder url = new StringBuilder();
		url.append("https://www.hipchat.com/v2/user/");
		url.append(userId);
		url.append("/message?auth_token=");
		url.append("okNe4C0SI4VJxg7xnLXz235HCi4OSMdqctPycz0W");

		template.postForLocation(url.toString(), message, String.class);
	}

	@Override
	public void broadcastMessage(String message) {
		Notification not = new Notification();
		not.setMessage(message);
		sendMessage("Warcry", not);
	}
}
