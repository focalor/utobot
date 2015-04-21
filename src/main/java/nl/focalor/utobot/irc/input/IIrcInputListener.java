package nl.focalor.utobot.irc.input;

import nl.focalor.utobot.irc.UtoPircBotX;

import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.events.MessageEvent;

public interface IIrcInputListener extends Listener<UtoPircBotX> {

	public void onMessage(MessageEvent<UtoPircBotX> event) throws Exception;
}
