package com.nrtl.pizza.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * SecurityContextProvider that uses spring security implementation.
 *
 * @author Ričardas Bučiūnas
 */
@Component
public class SpringSecuritySecurityContextProvider {

	public User getUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return (User) Optional.ofNullable(auth)
				.map(Authentication::getPrincipal)
				.filter(principal -> principal.getClass().isAssignableFrom(User.class))
				.orElse(null);
	}

}