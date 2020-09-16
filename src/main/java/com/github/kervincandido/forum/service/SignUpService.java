package com.github.kervincandido.forum.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.kervincandido.forum.controller.form.SignUpForm;
import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.repository.UserRepository;

@Service
public class SignUpService {
	
	@Autowired
	private UserRepository userRepository;

	public User create(SignUpForm userForm) throws IllegalArgumentException {
		User user = userForm.toUser();
		
		Optional<User> optionalUser = userRepository.findOptionalByEmail(user.getEmail());
		
		if (optionalUser.isPresent()) 
			throw new IllegalArgumentException("E-mail already registered");
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		User newUser = userRepository.save(user);
		return newUser;
	}
}
