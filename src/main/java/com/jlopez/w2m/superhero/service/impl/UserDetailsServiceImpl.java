package com.jlopez.w2m.superhero.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final String PASSWORD = "1234";
	private static final String USERNAME_USER = "user";
	private static final String USERNAME_MANAGER = "manager";
	private static final String USERNAME_ADMIN = "admin";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (USERNAME_USER.equals(username))
			return this.userBuilder(username, new BCryptPasswordEncoder().encode(PASSWORD), "USER");
		else if (USERNAME_MANAGER.equals(username))
			return this.userBuilder(username, new BCryptPasswordEncoder().encode(PASSWORD), "MANAGER");
		else if (USERNAME_ADMIN.equals(username))
			return this.userBuilder(username, new BCryptPasswordEncoder().encode(PASSWORD), "USER", "MANAGER", "ADMIN");
		else
			throw new UsernameNotFoundException("User Not found");
	}

	private User userBuilder(String username, String password, String... roles) {

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
				authorities);
	}

}
