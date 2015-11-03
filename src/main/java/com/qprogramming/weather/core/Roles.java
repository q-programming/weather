package com.qprogramming.weather.core;

public enum Roles {
	ROLE_USER, ROLE_ADMIN, ANNONYMOUS;

	public static Roles toRoles(String string) {
		return valueOf(string.toUpperCase());
	}

}
