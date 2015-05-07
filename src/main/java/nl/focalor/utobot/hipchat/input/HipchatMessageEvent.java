package nl.focalor.utobot.hipchat.input;

import nl.focalor.utobot.hipchat.model.User;

/**
 * @author focalor
 */
public class HipchatMessageEvent {
	private String room;
	private User user;
	private String message;
	private IHipchatInputListener dispatcher;

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public IHipchatInputListener getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(IHipchatInputListener dispatcher) {
		this.dispatcher = dispatcher;
	}

}
