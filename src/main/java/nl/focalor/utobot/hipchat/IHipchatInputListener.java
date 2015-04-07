package nl.focalor.utobot.hipchat;

public interface IHipchatInputListener {

	public void onRoomMessage(String room, String user, String message);
}
