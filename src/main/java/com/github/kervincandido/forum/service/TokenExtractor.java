package com.github.kervincandido.forum.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class TokenExtractor {
	
	public String extractRequestToken(HttpServletRequest request) {
		String tokenAndTypeToken = request.getHeader("Authorization");
		
		if (tokenAndTypeToken == null) {
			return null;
		}
		
		String[] tokenInfo = tokenAndTypeToken.trim().split(" ");
		return tokenInfo[1] != null ? tokenInfo[1] : null;
	}
	
	public String extractRequestToken(String tokenAndTypeToken) {
		if (tokenAndTypeToken == null) {
			return null;
		}
		
		String[] tokenInfo = tokenAndTypeToken.trim().split(" ");
		return tokenInfo[1] != null ? tokenInfo[1] : null;
	}
}
