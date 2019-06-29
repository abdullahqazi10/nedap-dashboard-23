package utwente.nedap.team23.nedapDashboard.model;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.message.filtering.SecurityEntityFilteringFeature;


public class Configuration extends ResourceConfig {


	public Configuration() {
		super();
		register(RolesAllowedDynamicFeature.class);
		register(SecurityEntityFilteringFeature.class);
		register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
				Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));
	}
}
