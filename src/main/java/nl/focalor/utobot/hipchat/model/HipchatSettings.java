package nl.focalor.utobot.hipchat.model;

import java.util.List;

/**
 * @author focalor
 */
public class HipchatSettings {
	private boolean active;
	private String token;
	private boolean xmppActive;
	private String xmppUser;
	private String xmppPassword;
	private List<String> rooms;
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

	public boolean isXmppActive() {
		return xmppActive;
	}

	public void setXmppActive(boolean xmppActive) {
		this.xmppActive = xmppActive;
	}

	public String getXmppUser() {
		return xmppUser;
	}

	public void setXmppUser(String xmppUser) {
		this.xmppUser = xmppUser;
	}

	public String getXmppPassword() {
		return xmppPassword;
	}

	public void setXmppPassword(String xmppPassword) {
		this.xmppPassword = xmppPassword;
	}

	public List<String> getRooms() {
		return rooms;
	}

	public void setRooms(List<String> rooms) {
		this.rooms = rooms;
	}

	public List<Webhook> getWebhooks() {
		return webhooks;
	}

	public void setWebhooks(List<Webhook> webhooks) {
		this.webhooks = webhooks;
	}

}
