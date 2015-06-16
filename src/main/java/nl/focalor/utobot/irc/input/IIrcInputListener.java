package nl.focalor.utobot.irc.input;

import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.irc.bot.UtoPircBotX;

import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

public interface IIrcInputListener extends IInputListener {

	public void onMessage(MessageEvent<UtoPircBotX> event) throws Exception;

	public void onJoin(JoinEvent<UtoPircBotX> event);
}
