package nl.focalor.utobot.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class UtobotPropertiesContextInitializer implements
		ApplicationContextInitializer<ConfigurableWebApplicationContext> {
	public static final Properties properties;
	public static final Map<String, Object> propertiesAsMap;

	static {
		properties = getUtobotProperties("utobot.properties");
		propertiesAsMap = convertToMap(properties);
	}

	@Override
	public void initialize(ConfigurableWebApplicationContext applicationContext) {
		MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
		MapPropertySource propertySource = new MapPropertySource("utobotPropeties", propertiesAsMap);
		propertySources.addLast(propertySource);
	}

	private static Map<String, Object> convertToMap(Properties properties) {
		Map<String, Object> result = new HashMap<>();
		for (final String name : properties.stringPropertyNames()) {
			result.put(name, properties.getProperty(name));
		}
		return result;
	}

	private static Properties getUtobotProperties(String propertiesFile) {
		try {
			Properties properties = new Properties();
			File file = new File(System.getProperty("user.home") + '/' + propertiesFile);
			if (file.exists()) {
				properties.load(new FileInputStream(file));
			} else {
				properties.load(UtobotPropertiesContextInitializer.class.getClassLoader().getResourceAsStream(
						propertiesFile));
			}
			return properties;
		} catch (IOException ex) {
			throw new RuntimeException("Failed loading properties", ex);
		}
	}

}
