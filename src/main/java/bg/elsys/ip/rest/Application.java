package bg.elsys.ip.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {
	public Application() {
		register(JacksonFeature.class);
	}
}
