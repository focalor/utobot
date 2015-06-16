package nl.focalor.utobot.hipchat.input;

/**
 * @author focalor
 */
public class HipchatMessageEvent extends HipchatEvent {
	private String room;
	private String message;

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}