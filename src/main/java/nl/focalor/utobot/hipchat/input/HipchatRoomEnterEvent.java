package nl.focalor.utobot.hipchat.input;


/**
 * @author focalor
 */
public class HipchatRoomEnterEvent extends HipchatEvent {
	private String room;

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
