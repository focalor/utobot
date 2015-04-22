package nl.focalor.utobot.irc.config;

import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.irc.input.IIrcInputListener;
import nl.focalor.utobot.irc.input.IrcInputListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author focalor
 */
@Configuration
//@formatter:off
@ComponentScan(basePackages = {
	"nl.focalor.utobot.irc.bot",
	"nl.focalor.utobot.irc.input",
	"nl.focalor.utobot.irc.service"
})
//@formatter:on
public class IrcConfig {
	@Bean
	public IIrcInputListener listener(IInputListener listener) {
		return new IrcInputListener(listener);
	}
}
