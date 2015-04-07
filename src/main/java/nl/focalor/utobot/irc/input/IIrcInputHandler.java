package nl.focalor.utobot.irc.input;

import java.util.List;
import nl.focalor.utobot.base.input.IResult;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author focalor
 */
public interface IIrcInputHandler {

	public List<String> getCommandNames();

	public IResult handleCommand(MessageEvent<PircBotX> event);
}
