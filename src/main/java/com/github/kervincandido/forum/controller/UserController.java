package com.github.kervincandido.forum.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kervincandido.forum.controller.dto.UserDTO;
import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id){
		
		Optional<User> optionalUser = userService.findById(id);
		
		if (optionalUser.isPresent()) {
			return ResponseEntity.ok(new UserDTO(optionalUser.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
}
