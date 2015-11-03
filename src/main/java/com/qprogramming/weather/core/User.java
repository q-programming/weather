package com.qprogramming.weather.core;

import java.security.Principal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
		@NamedQuery(name = "com.qprogramming.weather.core.User.findByUsername", query = "FROM User u WHERE u.username LIKE :username") })
@Table(name = "users")
public class User implements Principal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "username", nullable = false, unique = true)
	private final String username;
	@JsonIgnore
	private String password;
	@Enumerated(EnumType.STRING)
	private Roles role;

	public User() {
		this.username = "annonymous";
	}
	
	public User(String username) {
		this.username = username;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public int getId() {
		return (int) (Math.random() * 100);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public boolean getIsUser() {
		return getIsAdmin() || role.equals(Roles.ROLE_USER);
	}

	public boolean getIsAdmin() {
		return role.equals(Roles.ROLE_ADMIN);
	}

	@Override
	public String getName() {
		return getUsername();
	}

}
