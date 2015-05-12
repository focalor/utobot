package nl.focalor.utobot.util;

import nl.focalor.utobot.irc.bot.UtoPircBotX;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

@RunWith(MockitoJUnitRunner.class)
public abstract class TestBase {
	@Mock
	private static User user;
	@Mock
	private static Channel channel;

	public static MessageEvent<UtoPircBotX> buildMessageEvent(String message) {
		Configuration<UtoPircBotX> config = new Configuration.Builder<UtoPircBotX>().setServerHostname("localhost")
				.buildConfiguration();
		UtoPircBotX bot = new UtoPircBotX(config, null);

		return new MessageEvent<UtoPircBotX>(bot, channel, user, message);
	}
}
