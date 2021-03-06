package edu.awieclawski.web.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class ApiConfig extends ResourceConfig {
	public ApiConfig() {
		// Resources / Custom providers
		packages("edu.awieclawski.web.rest","com.fasterxml.jackson.jaxrs.json");
		// Binder used for injection
		register(new ApiBinder());
	}

}
