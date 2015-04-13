package nl.focalor.utobot;

import java.io.IOException;
import java.util.Properties;
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
		Properties properties = new Properties();
		properties.load(Main.class.getClassLoader().getResourceAsStream("utobot.properties"));

		Server server = new Server(Integer.valueOf(properties.getProperty("http.port")));
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

		return contextHandler;
	}

	private static WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("nl.focalor.utobot.base.config");
		return context;
	}
}
