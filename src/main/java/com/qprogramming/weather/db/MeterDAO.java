package com.qprogramming.weather.db;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.qprogramming.weather.WeatherConfiguration;
import com.qprogramming.weather.core.Meter;

import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.HibernateBundle;

public class MeterDAO extends AbstractDAO<Meter> {

	@Inject
	public MeterDAO(HibernateBundle<WeatherConfiguration> hibernate) {
		super(hibernate.getSessionFactory());
	}

	public Optional<Meter> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Meter create(Meter meter) {
		return persist(meter);
	}

	// TODO Auto-generated constructor stub

}
