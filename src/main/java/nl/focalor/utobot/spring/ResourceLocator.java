package nl.focalor.utobot.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author focalor
 */
public class ResourceLocator {

	public static InputStream open(String filename) {
		try {
			// Attempt full path
			File file = new File(filename);
			if (file.exists()) {
				return new FileInputStream(file);
			}

			// Attempt userhome path
			file = new File(System.getProperty("user.home") + File.separator + filename);
			if (file.exists()) {
				return new FileInputStream(file);
			}

			// Attempt classpath
			return ResourceLocator.class.getClassLoader().getResourceAsStream(filename);
		} catch (IOException ex) {
			throw new RuntimeException("Failed loading properties", ex);
		}
	}
}
