package nl.focalor.utobot.hipchat.config;

import java.io.IOException;
import nl.focalor.utobot.base.input.listener.IInputListener;
import nl.focalor.utobot.hipchat.HipchatInputListener;
import nl.focalor.utobot.hipchat.IHipchatInputListener;
import nl.focalor.utobot.hipchat.model.HipchatSettings;
import nl.focalor.utobot.hipchat.service.IHipchatService;
import nl.focalor.utobot.spring.ResourceLocator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author focalor
 */
@Configuration
//@formatter:off
@ComponentScan(basePackages = {
	"nl.focalor.utobot.hipchat.controller",
	"nl.focalor.utobot.hipchat.service"
})
//@formatter:on
public class HipchatConfig {
	@Bean
	public IHipchatInputListener hipchatCommandListener(IInputListener listener, IHipchatService service) {
		return new HipchatInputListener(listener, service);
	}

	@Bean
	public HipchatSettings hipchatSettings(ObjectMapper mapper, @Value("${settings.hipchat.file}") String settingsFile)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(ResourceLocator.open(settingsFile), HipchatSettings.class);
	}

}
