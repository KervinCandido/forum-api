package com.github.kervincandido.forum.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.kervincandido.forum.configuration.validation.FormErrorDTO;
import com.github.kervincandido.forum.controller.dto.UserDTO;
import com.github.kervincandido.forum.controller.form.SignUpForm;
import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.service.SignUpService;

@RestController
@RequestMapping("/signup")
public class SignUpControle {
	
	@Autowired
	private SignUpService signUpService;
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody @Valid SignUpForm signUpForm, UriComponentsBuilder uriComponentsBuilder) {
		try {
			User user = signUpService.create(signUpForm);
			URI uri = uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
			return ResponseEntity.created(uri).body(new UserDTO(user));
		} catch (IllegalArgumentException e) {
			FormErrorDTO formErrorDTO = new FormErrorDTO("email", e.getMessage());
			return ResponseEntity.badRequest().body(formErrorDTO);
		}
	}
}
