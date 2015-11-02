package com.qprogramming.weather.db;

import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.qprogramming.weather.core.Values;

import io.dropwizard.hibernate.AbstractDAO;

public class ValuesDAO extends AbstractDAO<Values> {

	public ValuesDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<Values> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Values create(Values value) {
		return persist(value);
	}

	public List<Values> findByMeter(long meterId) {
		return list(namedQuery("com.qprogramming.weather.core.Values.findByMeter").setLong("meter", meterId));
	}

	public List<Values> findByMeterAndDate(long meterId, DateTime date_from, DateTime date_to) {
		return list(namedQuery("com.qprogramming.weather.core.Values.findByMeterAndDate").setLong("meter", meterId)
				.setParameter("date_from", date_from).setParameter("date_to", date_to));
	}

	// TODO Auto-generated constructor stub

}
