package nl.focalor.utobot.irc.service;

import org.pircbotx.PircBotX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrcService implements IIrcService {
	@Autowired
	private PircBotX bot;

	@Override
	public void broadcastMessage(String message) {
		bot.sendIRC().message("#avians", message);
	}

}
