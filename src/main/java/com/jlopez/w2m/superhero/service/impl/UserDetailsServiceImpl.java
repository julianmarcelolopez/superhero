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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if ("user".equals(username))
			return this.userBuilder(username, new BCryptPasswordEncoder().encode("1234"), "USER");
		else if ("manager".equals(username))
			return this.userBuilder(username, new BCryptPasswordEncoder().encode("1234"), "MANAGER");
		else if ("admin".equals(username))
			return this.userBuilder(username, new BCryptPasswordEncoder().encode("1234"), "USER", "MANAGER", "ADMIN");
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
