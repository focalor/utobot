package nl.focalor.utobot.hipchat.input;

import nl.focalor.utobot.hipchat.model.User;

/**
 * @author focalor
 */
public abstract class HipchatEvent {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
