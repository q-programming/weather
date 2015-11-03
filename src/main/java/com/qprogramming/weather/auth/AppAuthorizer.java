package com.qprogramming.weather.auth;

import com.qprogramming.weather.core.Roles;
import com.qprogramming.weather.core.User;

import io.dropwizard.auth.Authorizer;

public class AppAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User user, String checkedRole) {
		Roles role = Roles.toRoles(checkedRole);
		switch (role) {
		case ROLE_USER:
			return user.getIsUser();
		case ROLE_ADMIN:
			return user.getIsAdmin();
		default:
			return false;
		}
	}
}
