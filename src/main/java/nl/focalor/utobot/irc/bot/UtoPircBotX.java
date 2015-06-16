package nl.focalor.utobot.irc.bot;

import java.io.IOException;

import nl.focalor.utobot.base.jobs.IStartupJob;
import nl.focalor.utobot.base.service.ILongInitialization;
import nl.focalor.utobot.irc.input.IIrcInputListener;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class UtoPircBotX extends PircBotX implements ILongInitialization {
	private final IIrcInputListener dispatcher;
	private final boolean active;
	private IStartupJob startupJob;

	public UtoPircBotX(Configuration<UtoPircBotX> config, IIrcInputListener dispatcher) {
		super(config);
		this.dispatcher = dispatcher;
		this.active = true;
	}

	@Autowired
	//@formatter:off
	public UtoPircBotX(
			@Value("${bot.name}") String name,
			@Value("${irc.channel.name}") String channel,
			@Value("${irc.channel.password}") String channelPassword,
			@Value("${irc.server}") String server,
			@Value("${irc.port}") int port,
			@Value("${irc.active}") boolean active,
			IIrcInputListener dispatcher) {
		//@formatter:on
		super(buildConfig(name, server, channel, channelPassword, port, dispatcher));
		this.dispatcher = dispatcher;
		this.active = active;
	}

	// Use setter to avoid circular dependencies

	@Autowired
	public void setStartupJob(IStartupJob startupJob) {
		this.startupJob = startupJob;
	}

	public IIrcInputListener getDispatcher() {
		return dispatcher;
	}

	private static Configuration<UtoPircBotX> buildConfig(String name, String server, String channel,
			String channelPassword, int port, final IIrcInputListener listener) {
		Builder<UtoPircBotX> configBuilder = new org.pircbotx.Configuration.Builder<UtoPircBotX>().setName(name)
				.setServer(server, port);
		if (StringUtils.isEmpty(channelPassword)) {
			configBuilder.addAutoJoinChannel(channel);
		} else {
			configBuilder.addAutoJoinChannel(channel, channelPassword);
		}
		configBuilder.addListener(new EventsListener(listener));
		configBuilder.addListener(new InitialisationCompleteListener());
		configBuilder.setAutoReconnect(true);

		return configBuilder.buildConfiguration();
	}

	@Override
	public void startBot() {
		try {
			if (active) {
				super.startBot();
			} else {
				initializationFinished();
			}
		} catch (IOException | IrcException ex) {
			throw new RuntimeException("Failed connecting to IRC", ex);
		}
	}

	@Override
	public void initializationFinished() {
		startupJob.registerFinishedInitialization(this);
	}

	private static class InitialisationCompleteListener extends ListenerAdapter<UtoPircBotX> {
		@Override
		public void onJoin(JoinEvent<UtoPircBotX> event) {
			if (event.getUser() == event.getBot().getUserBot()) {
				event.getBot().initializationFinished();
			}
		}
	}

	private static class EventsListener extends ListenerAdapter<UtoPircBotX> {
		private final IIrcInputListener inputListener;

		public EventsListener(IIrcInputListener inputListener) {
			super();
			this.inputListener = inputListener;
		}

		@Override
		public void onMessage(MessageEvent<UtoPircBotX> event) throws Exception {
			inputListener.onMessage(event);
		}

		@Override
		public void onJoin(JoinEvent<UtoPircBotX> event) {
			inputListener.onJoin(event);
		}
	}
}
