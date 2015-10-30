package com.qprogramming;

import com.qprogramming.health.TemplateHealthCheck;
import com.qprogramming.resources.StartResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WeatherApplication extends Application<WeatherConfiguration> {

	public static void main(final String[] args) throws Exception {
		new WeatherApplication().run(args);
	}

	@Override
	public String getName() {
		return "weather";
	}

	@Override
	public void initialize(final Bootstrap<WeatherConfiguration> bootstrap) {
		// TODO: application initialization
	}

	@Override
	public void run(final WeatherConfiguration configuration, final Environment environment) {
		// TODO: implement application
	    final TemplateHealthCheck healthCheck =
	            new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(new StartResource());
	}

}
