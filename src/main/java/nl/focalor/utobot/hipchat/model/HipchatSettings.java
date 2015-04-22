package nl.focalor.utobot.hipchat.model;

import java.util.List;

/**
 * @author focalor
 */
public class HipchatSettings {
	private boolean active;
	private String token;
	private String room;
	private List<Webhook> webhooks;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public List<Webhook> getWebhooks() {
		return webhooks;
	}

	public void setWebhooks(List<Webhook> webhooks) {
		this.webhooks = webhooks;
	}

}
