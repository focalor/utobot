package nl.focalor.utobot.irc.input;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public interface IIrcInputListener {

	public void onMessage(MessageEvent<PircBotX> event) throws Exception;
}
