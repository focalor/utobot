package nl.focalor.utobot.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.h2.util.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TestConfig {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource
				.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		populateDataSource(dataSource, "schema.sql");
		populateDataSource(dataSource, "data.sql");
		return dataSource;
	}

	private void populateDataSource(DataSource dataSource, String path) {
		try (Connection connection = dataSource.getConnection()) {
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream(path);
			String sql = IOUtils.readStringAndClose(IOUtils.getReader(in), -1);

			Statement statement = connection.createStatement();
			statement.execute(sql);
		} catch (SQLException | IOException ex) {
			throw new RuntimeException("Failed populating datasource", ex);
		}
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource datasource) {
		return new JdbcTemplate(datasource);
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
			DataSource datasource) {
		return new NamedParameterJdbcTemplate(datasource);
	}
}
