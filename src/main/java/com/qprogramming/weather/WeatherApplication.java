package com.qprogramming.weather;

import java.util.Set;

import javax.persistence.Entity;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.reflections.Reflections;

import com.qprogramming.weather.api.MeterApi;
import com.qprogramming.weather.auth.ApppAuthenticator;
import com.qprogramming.weather.auth.AppAuthorizer;
import com.qprogramming.weather.core.Meter;
import com.qprogramming.weather.core.User;
import com.qprogramming.weather.db.MeterDAO;
import com.qprogramming.weather.db.UserDAO;
import com.qprogramming.weather.db.ValuesDAO;
import com.qprogramming.weather.health.TemplateHealthCheck;
import com.qprogramming.weather.resources.IndexResource;
import com.qprogramming.weather.resources.StartResource;
import com.xeiam.dropwizard.sundial.SundialBundle;
import com.xeiam.dropwizard.sundial.SundialConfiguration;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
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

	private Class[] getEntities() {
		final Reflections reflections = new Reflections("com.qprogramming.weather.core");
		final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
		return classes.toArray(new Class[classes.size()]);
	}

	private final HibernateBundle<WeatherConfiguration> hibernate = new HibernateBundle<WeatherConfiguration>(
			Meter.class, getEntities()) {
		@Override
		public DataSourceFactory getDataSourceFactory(WeatherConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(final Bootstrap<WeatherConfiguration> bootstrap) {
		bootstrap.addBundle(new ViewBundle<WeatherConfiguration>());
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new AssetsBundle("/com/qprogramming/weather/assets/css", "/css", null, "css"));
		bootstrap.addBundle(new AssetsBundle("/com/qprogramming/weather/assets/js", "/js", null, "js"));
		bootstrap.addBundle(new SundialBundle<WeatherConfiguration>() {
			@Override
			public SundialConfiguration getSundialConfiguration(WeatherConfiguration configuration) {
				return configuration.getSundialConfiguration();
			}
		});
	}

	@Override
	public void run(final WeatherConfiguration configuration, final Environment environment) {
		final MeterDAO dao = new MeterDAO(hibernate.getSessionFactory());
		final ValuesDAO vDao = new ValuesDAO(hibernate.getSessionFactory());
		final UserDAO uDao = new UserDAO(hibernate.getSessionFactory());
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(new StartResource());
		environment.jersey().register(new IndexResource());
		environment.jersey().register(new MeterApi(dao, vDao));
		environment.jersey()
				.register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
						.setAuthenticator(new ApppAuthenticator(uDao, hibernate.getSessionFactory()))
						.setAuthorizer(new AppAuthorizer()).setRealm("Please signin to access this site")
						.buildAuthFilter()));
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.getApplicationContext().setAttribute("dataFile", configuration.getDataFile());
	}

}
