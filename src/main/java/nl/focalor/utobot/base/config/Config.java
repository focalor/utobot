package nl.focalor.utobot.base.config;

import java.io.IOException;

import javax.sql.DataSource;

import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.base.model.service.IMetadataService;
import nl.focalor.utobot.hipchat.HipchatInputListener;
import nl.focalor.utobot.hipchat.IHipchatInputListener;
import nl.focalor.utobot.hipchat.service.IHipchatService;
import nl.focalor.utobot.irc.input.IIrcInputListener;
import nl.focalor.utobot.irc.input.IrcInputListener;
import nl.focalor.utobot.util.Version;
import nl.focalor.utobot.utopia.model.UtopiaSettings;

import org.apache.commons.lang3.StringUtils;
import org.h2.Driver;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
// @formatter:off
@ComponentScan(basePackages = {
		"nl.focalor.utobot.base.input.handler",
		"nl.focalor.utobot.base.input.listener",
		"nl.focalor.utobot.base.model.dao",
		"nl.focalor.utobot.base.model.service",
		"nl.focalor.utobot.base.service",
		"nl.focalor.utobot.base.jobs",
		"nl.focalor.utobot.hipchat.controller",
		"nl.focalor.utobot.hipchat.service",
		"nl.focalor.utobot.irc.input",
		"nl.focalor.utobot.irc.service",
		"nl.focalor.utobot.utopia.dao",
		"nl.focalor.utobot.utopia.handler",
		"nl.focalor.utobot.utopia.service"
})
// @formatter:on
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

	// Utopia integration

	@Bean
	public UtopiaSettings utopiaSettings(ObjectMapper mapper, @Value("${settings.utopia.file}") String spellsFile)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(this.getClass().getClassLoader().getResource(spellsFile), UtopiaSettings.class);
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
	public DataSource datasource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource(new Driver(), "jdbc:h2:~/utopia");
		ds.setUsername("sa");

		return ds;
	}

	// Hipchat integration
	@Bean
	public IHipchatInputListener hipchatCommandListener(IInputListener listener, IHipchatService service) {
		return new HipchatInputListener(listener, service);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

	// IRC integration
	@Bean
	public IIrcInputListener listener(IInputListener listener) {
		return new IrcInputListener(listener);
	}

	private static final String NAME = "WarNub2";
	private static final String SERVER = "irc.utonet.org";
	private static final int PORT = 6667;
	private static final String CHANNEL = "#avians";
	private static final String CHANNEL_PASS = "masterpassword";

	@Bean
	public PircBotX bot(IIrcInputListener listener) throws IOException, IrcException {
		Builder<PircBotX> configBuilder = new org.pircbotx.Configuration.Builder<>().setName(NAME).setServer(SERVER,
				PORT);
		if (StringUtils.isEmpty(CHANNEL_PASS)) {
			configBuilder.addAutoJoinChannel(CHANNEL);
		} else {
			configBuilder.addAutoJoinChannel(CHANNEL, CHANNEL_PASS);
		}
		configBuilder.addListener(listener);
		configBuilder.setAutoReconnect(true);

		return new PircBotX(configBuilder.buildConfiguration());
	}
}
