package com.github.kervincandido.forum.configuration.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.service.TokenStringExtractorService;
import com.github.kervincandido.forum.service.TokenService;
import com.github.kervincandido.forum.service.UserService;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserService userService;
	private TokenStringExtractorService tokenExtractor;
	
	public TokenAuthenticationFilter(TokenService tokenService, TokenStringExtractorService tokenExtractor, UserService userService) {
		this.tokenService = tokenService;
		this.tokenExtractor = tokenExtractor;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = tokenExtractor.extractRequestToken(request);
		
		if (isValidToken(token)) {
			autenticateUser(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private boolean isValidToken(String token) {
		return token != null && !token.trim().isEmpty() && 
				!token.startsWith("Bearer ") && 
				tokenService.isValidToken(token);
	}

	private void autenticateUser(String token) {
		Long userId = tokenService.getUserId(token);
		Optional<User> optionalUser = userService.findById(userId);
		
		optionalUser.ifPresent(user -> {
			SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
		});
	}
}
