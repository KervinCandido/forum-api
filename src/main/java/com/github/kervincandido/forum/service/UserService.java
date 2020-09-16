package com.github.kervincandido.forum.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private TokenExtractorService tokenExtractor;

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public Optional<User> getLoggedUser() {
		String extractedToken = tokenExtractor.extractRequestToken(request);
		
		if (extractedToken == null || !tokenService.isValidToken(extractedToken)) {
			return Optional.empty();
		}
		
		Long userId = tokenService.getUserId(extractedToken);
		return userRepository.findById(userId);
	}
}
