package nl.focalor.utobot.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import nl.focalor.utobot.base.input.CommandLineInputListener;
import nl.focalor.utobot.base.input.IInputListener;
import nl.focalor.utobot.base.input.InputListener;
import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.base.model.service.IMetadataService;
import nl.focalor.utobot.hipchat.HipchatInputListener;
import nl.focalor.utobot.hipchat.IHipchatInputListener;
import nl.focalor.utobot.hipchat.service.IHipchatService;
import nl.focalor.utobot.util.Version;

import org.h2.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
		"nl.focalor.utobot.base.model.dao",
		"nl.focalor.utobot.base.model.service",
		"nl.focalor.utobot.base.service",
		"nl.focalor.utobot.base.jobs",
		"nl.focalor.utobot.hipchat.controller",
		"nl.focalor.utobot.hipchat.service",
		//"nl.focalor.utobot.irc.service",
		"nl.focalor.utobot.utopia.dao",
		"nl.focalor.utobot.utopia.handler",
		"nl.focalor.utobot.utopia.service"
})
//@formatter:on
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:utobot.properties")
public class Config {
	// Property resolving
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// JSon mappers
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public HttpMessageConverter<Object> messageConverter() {
		return new MappingJackson2HttpMessageConverter(objectMapper());
	}

	// Input handling
	@Bean
	public IInputListener inputListener(
			List<IInputHandlerFactory> inputHandlerFactories,
			List<IRegexHandler> regexHandlers,
			List<ICommandHandler> commandHandlers) {
		List<IRegexHandler> combinedRegexHandlers = new ArrayList<>();
		List<ICommandHandler> combinedCommandHandlers = new ArrayList<>();
		combinedRegexHandlers.addAll(regexHandlers);
		combinedCommandHandlers.addAll(commandHandlers);

		for (IInputHandlerFactory factory : inputHandlerFactories) {
			combinedRegexHandlers.addAll(factory.getRegexHandlers());
			combinedCommandHandlers.addAll(factory.getCommandHandlers());
		}

		return new InputListener(combinedRegexHandlers, combinedCommandHandlers);
	}

	@Bean
	public CommandLineInputListener reader(IInputListener listener,
			@Value("${bot.name}") String botname) {
		CommandLineInputListener result = new CommandLineInputListener(
				listener, botname);
		result.start();
		return result;
	}

	// Hipchat integration
	@Bean
	public IHipchatInputListener hipchatCommandListener(
			IInputListener listener, IHipchatService service) {
		return new HipchatInputListener(listener, service);
	}

	// Database
	@Bean
	public Version dbVersion(IMetadataService service) {
		return service.getSchemaVersion();
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
