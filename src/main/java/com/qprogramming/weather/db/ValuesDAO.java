package com.qprogramming.weather.db;

import java.util.List;

import org.hibernate.SessionFactory;

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

	// TODO Auto-generated constructor stub

}
