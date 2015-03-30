package nl.focalor.utobot.hipchat.model;

public class RoomMessage {
	private String event;
	private RoomMessageItem item;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public RoomMessageItem getItem() {
		return item;
	}

	public void setItem(RoomMessageItem item) {
		this.item = item;
	}

}
