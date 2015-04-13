package nl.focalor.utobot.irc.input;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.events.MessageEvent;

public interface IIrcInputListener extends Listener<PircBotX> {

	public void onMessage(MessageEvent<PircBotX> event) throws Exception;
}
