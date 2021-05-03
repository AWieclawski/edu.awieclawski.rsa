package edu.awieclawski.web.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class ApiConfig extends ResourceConfig {
	public ApiConfig() {
		packages("edu.awieclawski.web.rest");
	}

}
