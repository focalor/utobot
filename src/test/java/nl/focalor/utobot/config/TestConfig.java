package nl.focalor.utobot.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TestConfig {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource datasource) {
		return new NamedParameterJdbcTemplate(datasource);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.H2);
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}
	// TODO enable once test properties are loaded
	// @Bean
	// public LocalContainerEntityManagerFactoryBean entityManagerFactory(
	// @Value("${hibernate.dialect}") String hiernateDialect,
	// @Value("${hibernate.hbm2ddl.auto}") String hibernateHbm2ddlAuto,
	// @Value("${hibernate.show_sql}") String hibernateShowSql,
	// @Value("${hibernate.format_sql}") String hibernateFormatSql) {
	// LocalContainerEntityManagerFactoryBean lemfb = new
	// LocalContainerEntityManagerFactoryBean();
	//
	// lemfb.setDataSource(dataSource());
	// lemfb.setJpaVendorAdapter(jpaVendorAdapter());
	// lemfb.setPackagesToScan("nl.focalor.utobot");
	//
	// Properties jpaProperties = new Properties();
	// jpaProperties.put("hibernate.dialect", hiernateDialect);
	// jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
	// jpaProperties.put("hibernate.show_sql", hibernateShowSql);
	// jpaProperties.put("hibernate.format_sql", hibernateFormatSql);
	// lemfb.setJpaProperties(jpaProperties);
	//
	// return lemfb;
	// }
}
