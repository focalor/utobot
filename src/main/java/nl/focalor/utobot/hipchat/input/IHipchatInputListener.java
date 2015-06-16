package nl.focalor.utobot.hipchat.input;

import nl.focalor.utobot.base.input.listener.IInputListener;

public interface IHipchatInputListener extends IInputListener {

	public void onRoomMessage(HipchatMessageEvent event);
}
