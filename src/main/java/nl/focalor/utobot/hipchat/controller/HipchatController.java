package nl.focalor.utobot.hipchat.controller;

import nl.focalor.utobot.hipchat.input.HipchatMessageEvent;
import nl.focalor.utobot.hipchat.input.HipchatRoomEnterEvent;
import nl.focalor.utobot.hipchat.input.IHipchatInputListener;
import nl.focalor.utobot.hipchat.model.RoomEnter;
import nl.focalor.utobot.hipchat.model.RoomEnterItem;
import nl.focalor.utobot.hipchat.model.RoomMessage;
import nl.focalor.utobot.hipchat.model.RoomMessageItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hipchat")
public class HipchatController {
	@Autowired
	private IHipchatInputListener listener;

	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "roomMessage", method = RequestMethod.POST)
	public void roomMessage(@RequestBody RoomMessage roomMessage) {
		HipchatMessageEvent event = new HipchatMessageEvent();

		RoomMessageItem item = roomMessage.getItem();
		event.setRoom(item.getRoom().getId());
		event.setUser(item.getMessage().getFrom());
		event.setMessage(item.getMessage().getMessage());

		listener.onRoomMessage(event);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "roomEnter", method = RequestMethod.POST)
	public void roomEnter(@RequestBody RoomEnter roomEnter) {
		HipchatRoomEnterEvent event = new HipchatRoomEnterEvent();

		RoomEnterItem item = roomEnter.getItem();
		event.setRoom(item.getRoom().getId());
		event.setUser(item.getSender());

		listener.onRoomEnter(event);
	}
}