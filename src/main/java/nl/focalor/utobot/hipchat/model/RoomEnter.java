package nl.focalor.utobot.hipchat.model;

/**
 * @author focalor
 */
public class RoomEnter {
	private String event;
	private RoomEnterItem item;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public RoomEnterItem getItem() {
		return item;
	}

	public void setItem(RoomEnterItem item) {
		this.item = item;
	}

}
