package nl.focalor.utobot.irc.service;

import nl.focalor.utobot.irc.UtoPircBotX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrcService implements IIrcService {
	@Autowired
	private UtoPircBotX bot;

	@Override
	public void broadcastMessage(String message) {
		if (bot.isConnected()) {
			bot.sendIRC().message("#avians", message);
		}
	}

}
