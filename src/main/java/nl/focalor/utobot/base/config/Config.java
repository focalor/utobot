package nl.focalor.utobot.base.config;

import java.io.IOException;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import nl.focalor.utobot.base.model.BaseSettings;
import nl.focalor.utobot.spring.ResourceLocator;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
		"nl.focalor.utobot.hipchat.config",
		"nl.focalor.utobot.irc.config",
		"nl.focalor.utobot.utopia.config",
})
// @formatter:on
@EnableWebMvc
@EnableTransactionManagement
public class Config {

	@Bean
	public BaseSettings baseSettings(ObjectMapper mapper, @Value("${settings.base.file}") String settingsFile)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(ResourceLocator.open(settingsFile), BaseSettings.class);
	}

	// Property resolving
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// JSon mappers
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper;
	}

	@Bean
	public HttpMessageConverter<Object> messageConverter() {
		return new MappingJackson2HttpMessageConverter(objectMapper());
	}

	// Database

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource(new Driver(), "jdbc:h2:~/utopia");
		ds.setUsername("sa");

		return ds;
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

		lemfb.setDataSource(dataSource());
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
