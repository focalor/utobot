package nl.focalor.utobot.base.config;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.hipchat.HipchatInputListener;
import nl.focalor.utobot.hipchat.IHipchatInputListener;
import nl.focalor.utobot.hipchat.service.IHipchatService;
import nl.focalor.utobot.irc.input.IIrcInputListener;
import nl.focalor.utobot.irc.input.IrcInputListener;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
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
@EnableJpaRepositories("nl.focalor.utobot")
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

	// IRC integration
	@Bean
	public IIrcInputListener listener(IInputListener listener) {
		return new IrcInputListener(listener);
	}

	//@formatter:off
	@Bean
	public PircBotX bot(
			@Value("${bot.name}") String name,
			@Value("${irc.channel.name}") String channel,
			@Value("${irc.channel.password}") String channelPassword,
			@Value("${irc.server}") String server,
			@Value("${irc.port}") int port,
			IIrcInputListener listener) {
	//@formatter:on
		// Configure bot
		Builder<PircBotX> configBuilder = new org.pircbotx.Configuration.Builder<>().setName(name).setServer(server,
				port);
		if (StringUtils.isEmpty(channelPassword)) {
			configBuilder.addAutoJoinChannel(channel);
		} else {
			configBuilder.addAutoJoinChannel(channel, channelPassword);
		}
		configBuilder.addListener(listener);
		configBuilder.setAutoReconnect(true);

		PircBotX bot = new PircBotX(configBuilder.buildConfiguration());

		// Start bot in other thread to avoid blocking
		new Thread() {
			@Override
			public void run() {
				try {
					bot.startBot();
				} catch (IOException | IrcException ex) {
					throw new RuntimeException("Failed connecting to IRC", ex);
				}
			}
		}.start();

		return bot;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.H2);
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Value("${hibernate.dialect}") String hiernateDialect,
			@Value("${hibernate.hbm2ddl.auto}") String hibernateHbm2ddlAuto,
			@Value("${hibernate.show_sql}") String hibernateShowSql,
			@Value("${hibernate.format_sql}") String hibernateFormatSql) {
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();

		lemfb.setDataSource(datasource());
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan("nl.focalor.utobot");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", hiernateDialect);
		jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		jpaProperties.put("hibernate.show_sql", hibernateShowSql);
		jpaProperties.put("hibernate.format_sql", hibernateFormatSql);
		lemfb.setJpaProperties(jpaProperties);

		return lemfb;
	}
}
