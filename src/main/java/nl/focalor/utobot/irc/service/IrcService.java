package nl.focalor.utobot.irc.service;

import nl.focalor.utobot.irc.bot.UtoPircBotX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IrcService implements IIrcService {
	@Autowired
	private UtoPircBotX bot;
	@Value("${irc.channel.name}")
	private String mainChannel;

	@Override
	public void broadcast(String message) {
		if (bot.isConnected()) {
			bot.sendIRC().message(mainChannel, message);
		}
	}

	@Override
	public void setTopic(String topic) {
		if (bot.isConnected()) {
			bot.sendIRC().message("chanserv", "topic " + mainChannel + ' ' + topic);
		}
	}

}
