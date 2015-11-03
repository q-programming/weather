package com.qprogramming.weather.db;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.qprogramming.weather.core.User;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<User> {

	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<User> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Optional<User> findByName(String username) {
		return Optional.fromNullable(uniqueResult(
				namedQuery("com.qprogramming.weather.core.User.findByUsername").setParameter("username", username)));
	}

	public User create(User user) {
		return persist(user);
	}

	// TODO Auto-generated constructor stub

}
