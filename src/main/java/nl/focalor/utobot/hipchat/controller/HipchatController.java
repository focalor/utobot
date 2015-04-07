package nl.focalor.utobot.hipchat.controller;

import nl.focalor.utobot.hipchat.IHipchatInputListener;
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
	@RequestMapping(value = "command", method = RequestMethod.POST)
	public void command(@RequestBody RoomMessage roomMessage) {
		RoomMessageItem item = roomMessage.getItem();

		String roomId = item.getRoom().getId();
		String user = item.getMessage().getFrom().getName();
		String message = item.getMessage().getMessage();
		listener.onRoomMessage(roomId, user, message);
	}
}
