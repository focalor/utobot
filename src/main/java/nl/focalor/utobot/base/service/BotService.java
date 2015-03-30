package nl.focalor.utobot.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotService implements IBotService {
	@Autowired
	private List<IMessagingService> messagingServices;

	@Override
	public void broadcast(String message) {
		for (IMessagingService service : messagingServices) {
			service.broadcastMessage(message);
		}
	}
}
