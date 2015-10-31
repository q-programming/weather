package com.qprogramming.weather.db;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.qprogramming.weather.core.Meter;

import io.dropwizard.hibernate.AbstractDAO;

public class MeterDAO extends AbstractDAO<Meter> {

	public MeterDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<Meter> findById(Long id) {
		return Optional.fromNullable(get(id));
	}
	
    public Meter create(Meter meter) {
        return persist(meter);	
    }
	

	// TODO Auto-generated constructor stub

}
