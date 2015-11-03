package com.qprogramming.weather.auth;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.google.common.base.Optional;
import com.qprogramming.weather.core.Roles;
import com.qprogramming.weather.core.User;
import com.qprogramming.weather.db.UserDAO;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

public class ApppAuthenticator implements Authenticator<BasicCredentials, User> {

	private UserDAO uDao;
	private SessionFactory sessionFactory;

	public ApppAuthenticator(UserDAO uDao, SessionFactory sessionFactory) {
		this.uDao = uDao;
		this.sessionFactory = sessionFactory;
	}

	@Override
	@UnitOfWork
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		// open new session to validate user
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		Session session = sessionFactory.openSession();
		session.setDefaultReadOnly(true);
		session.setCacheMode(CacheMode.NORMAL);
		session.setFlushMode(FlushMode.MANUAL);
		ManagedSessionContext.bind(session);
		Optional<User> user = uDao.findByName(credentials.getUsername());
		if (user.isPresent()) {
			if (passwordEncryptor.checkPassword(credentials.getPassword(), user.get().getPassword())) {
				return user;
			} else {
				return Optional.absent();
			}
		} else {
			User newuser = new User(credentials.getUsername());
			newuser.setRole(Roles.ROLE_USER);
			newuser.setPassword(passwordEncryptor.encryptPassword(credentials.getPassword()));
			uDao.create(newuser);
		}
		return Optional.absent();
	}
}
