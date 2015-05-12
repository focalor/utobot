package nl.focalor.utobot.irc.config;

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
}
