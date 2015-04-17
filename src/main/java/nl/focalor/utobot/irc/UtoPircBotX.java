package nl.focalor.utobot.irc;

import java.io.IOException;
import nl.focalor.utobot.base.jobs.IStartupJob;
import nl.focalor.utobot.base.service.ILongInitialization;
import nl.focalor.utobot.irc.input.IIrcInputListener;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class UtoPircBotX extends PircBotX implements ILongInitialization {
	private IStartupJob startupJob;

	@Autowired
	//@formatter:off
	public UtoPircBotX(
			@Value("${bot.name}") String name,
			@Value("${irc.channel.name}") String channel,
			@Value("${irc.channel.password}") String channelPassword,
			@Value("${irc.server}") String server,
			@Value("${irc.port}") int port,
			IIrcInputListener listener) {
	//@formatter:on
		super(buildConfig(name, server, channel, channelPassword, port, listener));
	}

	// User setter to avoid circular dependencies
	@Autowired
	public void setStartupJob(IStartupJob startupJob) {
		this.startupJob = startupJob;
	}

	private static Configuration<PircBotX> buildConfig(String name, String server, String channel,
			String channelPassword, int port, IIrcInputListener listener) {
		Builder<PircBotX> configBuilder = new org.pircbotx.Configuration.Builder<>().setName(name).setServer(server,
				port);
		if (StringUtils.isEmpty(channelPassword)) {
			configBuilder.addAutoJoinChannel(channel);
		} else {
			configBuilder.addAutoJoinChannel(channel, channelPassword);
		}
		configBuilder.addListener(listener);
		configBuilder.setAutoReconnect(true);

		return configBuilder.buildConfiguration();
	}

	@Override
	public void startBot() {
		try {
			super.startBot();
			initializationFinished();
		} catch (IOException | IrcException ex) {
			throw new RuntimeException("Failed connecting to IRC", ex);
		}
	}

	@Override
	public void initializationFinished() {
		startupJob.registerFinishedInitialization(this);
	}

}
