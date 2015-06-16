package nl.focalor.utobot.hipchat.model;

/**
 * @author focalor
 */
public class RoomEnterItem {
	private User sender;
	private Room room;

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}