package edu.awieclawski.web.rest;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApiBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bind(ApiUtils.class).to(ApiUtils.class).in(Singleton.class);
	}
}
