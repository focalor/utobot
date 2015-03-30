package nl.focalor.utobot.hipchat;

import nl.focalor.utobot.base.input.IInputListener;

public interface IHipchatInputListener extends IInputListener {

	public void onRoomMessage(String room, String message);
}
