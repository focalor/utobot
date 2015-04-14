package nl.focalor.utobot;

import java.io.IOException;

import nl.focalor.utobot.base.config.Config;
import nl.focalor.utobot.spring.UtobotPropertiesContextInitializer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author hj.van.veenendaal
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		String port = UtobotPropertiesContextInitializer.properties.getProperty("http.port");

		Server server = new Server(Integer.valueOf(port));
		server.setHandler(getServletContextHandler());
		server.start();
		server.join();
	}

	private static ServletContextHandler getServletContextHandler() throws IOException {
		WebApplicationContext context = getContext();

		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setContextPath("/");
		contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
		contextHandler.addEventListener(new ContextLoaderListener(context));
		contextHandler.setInitParameter("contextInitializerClasses",
				"nl.focalor.utobot.spring.UtobotPropertiesContextInitializer");
		return contextHandler;
	}

	private static WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(Config.class);
		return context;
	}
}
