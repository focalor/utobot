package nl.focalor.utobot.config;

import java.util.List;

import javax.sql.DataSource;

import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.hipchat.HipchatInputListener;
import nl.focalor.utobot.hipchat.service.IHipchatService;
import nl.focalor.utobot.model.service.IMetadataService;
import nl.focalor.utobot.util.Version;

import org.h2.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
//@formatter:off
@ComponentScan(basePackages = {
		"nl.focalor.utobot.base.input.handler",
		"nl.focalor.utobot.base.service",
		"nl.focalor.utobot.base.jobs",
		"nl.focalor.utobot.hipchat.controller",
		"nl.focalor.utobot.hipchat.service",
		"nl.focalor.utobot.irc.service",
		"nl.focalor.utobot.model.dao",
		"nl.focalor.utobot.model.service",
		"nl.focalor.utobot.utopia.dao",
		"nl.focalor.utobot.utopia.handler",
		"nl.focalor.utobot.utopia.service"
})
//@formatter:on
@EnableWebMvc
@EnableTransactionManagement
public class Config {
	// JSon mappers
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public HttpMessageConverter<Object> messageConverter() {
		return new MappingJackson2HttpMessageConverter(objectMapper());
	}

	// Utopia stuff

	// Hipchat integration
	@Bean
	public HipchatInputListener hipchatCommandListener(
			List<ICommandHandler> commandHandlers,
			List<IRegexHandler> regexHandlers, IHipchatService service) {
		HipchatInputListener result = new HipchatInputListener(regexHandlers,
				service);
		result.setCommandHandlers(commandHandlers);
		return result;
	}

	// Database
	@Bean
	public Version dbVersion(IMetadataService service) {
		return service.upgradeToCurrentVersion();
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(datasource());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(datasource());
	}

	@Bean
	public DataSource datasource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource(new Driver(),
				"jdbc:h2:~/utopia");
		ds.setUsername("sa");

		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

	// private static final String NAME = "WarNub2";
	// private static final String SERVER = "irc.utonet.org";
	// private static final int PORT = 6667;
	// private static final String CHANNEL = "#avians";
	// private static final String CHANNEL_PASS = "masterpassword";
	//
	// @Bean
	// public IrcCommandListener listener(List<ICommandHandler> handlers) {
	// return new IrcCommandListener(handlers);
	// }
	//
	// @Bean
	// public PircBotX bot(IrcCommandListener listener) throws IOException,
	// IrcException {
	// Builder<PircBotX> configBuilder = new
	// org.pircbotx.Configuration.Builder<>()
	// .setName(NAME).setServer(SERVER, PORT);
	// if (StringUtils.isEmpty(CHANNEL_PASS)) {
	// configBuilder.addAutoJoinChannel(CHANNEL);
	// } else {
	// configBuilder.addAutoJoinChannel(CHANNEL, CHANNEL_PASS);
	// }
	// configBuilder.addListener(listener);
	//
	// PircBotX bot = new PircBotX(configBuilder.buildConfiguration());
	// bot.startBot();
	// return bot;
	// }
}
