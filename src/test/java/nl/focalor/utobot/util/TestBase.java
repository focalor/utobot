package nl.focalor.utobot.util;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

@RunWith(MockitoJUnitRunner.class)
public abstract class TestBase {
	@Mock
	private static User user;
	@Mock
	private static Channel channel;

	public static MessageEvent<PircBotX> buildMessageEvent(String message) {
		Configuration<PircBotX> config = new Configuration.Builder<PircBotX>()
				.setServerHostname("localhost").buildConfiguration();
		PircBotX bot = new PircBotX(config);

		return new MessageEvent<PircBotX>(bot, channel, user, message);
	}
}
