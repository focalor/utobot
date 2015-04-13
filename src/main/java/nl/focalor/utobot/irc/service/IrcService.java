package nl.focalor.utobot.irc.service;

import javax.annotation.PostConstruct;

import org.pircbotx.PircBotX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrcService implements IIrcService {
	@Autowired
	private PircBotX bot;

	@PostConstruct
	public void run() {
		// bot.startBot();
	}

	@Override
	public void broadcastMessage(String message) {
		bot.sendIRC().message("#avians", message);
	}

}
