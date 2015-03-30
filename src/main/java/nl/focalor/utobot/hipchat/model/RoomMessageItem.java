package nl.focalor.utobot.hipchat.model;

public class RoomMessageItem {
	private Message message;
	private Room room;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}
