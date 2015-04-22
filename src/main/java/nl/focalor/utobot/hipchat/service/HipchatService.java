package nl.focalor.utobot.hipchat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import nl.focalor.utobot.hipchat.model.HipchatSettings;
import nl.focalor.utobot.hipchat.model.Message;
import nl.focalor.utobot.hipchat.model.Notification;
import nl.focalor.utobot.hipchat.model.Webhook;
import nl.focalor.utobot.hipchat.model.Webhooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HipchatService implements IHipchatService {
	private static final String API_URL = "https://www.hipchat.com/v2/";
	private static final String API_ROOM_URL = API_URL + "room/";
	private static final String API_USER_URL = API_URL + "user/";

	private final boolean active;
	private final String authToken;
	private final String mainRoom;
	private final Map<String, Webhook> utobotWebhooks;
	private final RestTemplate template;

	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	public HipchatService(HipchatSettings settings) {
		this.active = settings.isActive();
		this.authToken = settings.getToken();
		this.mainRoom = settings.getRoom();

		this.template = new RestTemplate();
		this.utobotWebhooks = new HashMap<>();
		for (Webhook hook : settings.getWebhooks()) {
			this.utobotWebhooks.put(hook.getName(), hook);
		}
	}

	@PostConstruct
	public void init() {
		updateWebhooks(mainRoom);
	}

	@Override
	public void registerHook(String room, Webhook webhook) {
		StringBuilder url = new StringBuilder();
		url.append(API_ROOM_URL);
		url.append(room);
		url.append("/webhook");

		post(url, webhook);
	}

	@Override
	public void sendMessage(String room, Notification message) {
		StringBuilder url = new StringBuilder();
		url.append(API_ROOM_URL);
		url.append(room);
		url.append("/notification");

		post(url, message);
	}

	@Override
	public void sendPrivateMessage(int userId, Message message) {
		StringBuilder url = new StringBuilder();
		url.append(API_USER_URL);
		url.append(userId);
		url.append("/message");

		post(url, message);
	}

	@Override
	public void broadcastMessage(String message) {
		Notification not = new Notification();
		not.setMessage(message);
		sendMessage(mainRoom, not);
	}

	@Override
	public void updateWebhooks(String roomId) {
		if (!active) {
			return;
		}

		List<Webhook> existingHooks = getUtobotWebhooks(roomId);
		// Delete unused hooks
		for (Webhook hook : existingHooks) {
			if (!utobotWebhooks.containsKey(hook.getName())) {
				deleteWebhook(roomId, hook.getId());
			}
		}

		// Add missing hooks
		for (Entry<String, Webhook> entry : utobotWebhooks.entrySet()) {
			if (webhookNameExists(existingHooks, entry.getKey())) {
				continue;
			}

			createWebhook(roomId, entry.getValue());
		}
	}

	private boolean webhookNameExists(List<Webhook> existingHooks, String webhookName) {
		for (Webhook existingHook : existingHooks) {
			if (webhookName.equals(existingHook.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Webhook> getUtobotWebhooks(String roomId) {
		//@formatter:off
		return getWebhooks(roomId).getItems().stream()
				.filter(hook -> hook.getName().startsWith("utobot"))
				.collect(Collectors.toList());
		//@formatter:on
	}

	@Override
	public void deleteWebhook(String roomId, Long webhookId) {
		StringBuilder url = new StringBuilder();
		url.append(API_ROOM_URL);
		url.append(roomId);
		url.append("/webhook/");
		url.append(webhookId);

		delete(url);
	}

	@Override
	public void createWebhook(String roomId, Webhook webhook) {
		StringBuilder url = new StringBuilder();
		url.append(API_ROOM_URL);
		url.append(roomId);
		url.append("/webhook");

		post(url, webhook);
	}

	@Override
	public Webhooks getWebhooks(String roomId) {
		StringBuilder url = new StringBuilder();
		url.append(API_ROOM_URL);
		url.append(roomId);
		url.append("/webhook");

		return get(url, Webhooks.class);
	}

	private String addAuthentication(CharSequence url) {
		StringBuilder builder = new StringBuilder();
		builder.append(url);
		return addAuthentication(builder);
	}

	private String addAuthentication(StringBuilder url) {
		url.append("?auth_token=");
		url.append(authToken);
		return url.toString();
	}

	private <T> T get(CharSequence url, Class<T> responseType) {
		if (active) {
			ResponseEntity<T> response = template.getForEntity(addAuthentication(url), responseType);
			return response.getBody();
		} else {
			return null;
		}
	}

	private void post(CharSequence url, Object request) {
		if (active) {
			template.postForLocation(addAuthentication(url), request);
		}
	}

	private void delete(CharSequence url) {
		if (active) {
			template.delete(addAuthentication(url));
		}
	}
}
