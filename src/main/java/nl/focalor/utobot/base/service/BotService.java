package nl.focalor.utobot.base.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotService implements IBotService {
	@Autowired
	private PircBotX bot;
	@Autowired
	private List<ICommunicationService> communicationServices;

	@Override
	@PostConstruct
	public void startBot() {
		new Thread(() -> {
			try {
				bot.startBot();
			} catch (IOException | IrcException ex) {
				throw new RuntimeException("Failed starting server", ex);
			}
		}).start();
	}

	@Override
	public void broadcast(String message) {
		for (ICommunicationService service : communicationServices) {
			service.broadcast(message);
		}
	}

	@Override
	public void broadcast(List<String> messages) {
		for (ICommunicationService service : communicationServices) {
			service.broadcast(messages);
		}
	}

	@Override
	public void setTopic(String topic) {
		for (ICommunicationService service : communicationServices) {
			service.setTopic(topic);
		}
	}
}
