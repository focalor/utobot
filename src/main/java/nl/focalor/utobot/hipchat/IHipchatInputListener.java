package nl.focalor.utobot.hipchat;

import nl.focalor.utobot.hipchat.model.User;

public interface IHipchatInputListener {

	public void onRoomMessage(String room, User user, String message);
}
