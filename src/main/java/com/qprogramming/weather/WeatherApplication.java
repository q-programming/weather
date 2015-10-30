package com.qprogramming.weather;

import com.qprogramming.weather.health.TemplateHealthCheck;
import com.qprogramming.weather.resources.IndexResource;
import com.qprogramming.weather.resources.StartResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

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
		bootstrap.addBundle(new ViewBundle<WeatherConfiguration>());
	}

	@Override
	public void run(final WeatherConfiguration configuration, final Environment environment) {
		// TODO: implement application
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(new StartResource());
		environment.jersey().register(new IndexResource());
	}

}
