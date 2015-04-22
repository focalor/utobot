package nl.focalor.utobot.utopia.config;

import java.io.IOException;
import nl.focalor.utobot.spring.ResourceLocator;
import nl.focalor.utobot.utopia.model.UtopiaSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author focalor
 */
@Configuration
//@formatter:off
@ComponentScan(basePackages = {
		"nl.focalor.utobot.utopia.dao",
		"nl.focalor.utobot.utopia.handler",
		"nl.focalor.utobot.utopia.service"
})
//@formatter:on
@EnableJpaRepositories("nl.focalor.utobot")
public class UtopiaConfig {
	@Bean
	public UtopiaSettings utopiaSettings(ObjectMapper mapper, @Value("${settings.utopia.file}") String settingsFile)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(ResourceLocator.open(settingsFile), UtopiaSettings.class);
	}
}
