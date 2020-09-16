package com.github.kervincandido.forum.configuration.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.service.UserService;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optionalUser = service.findByEmail(email);
		return optionalUser.orElseThrow(() -> new UsernameNotFoundException("email not found"));
	}

}
