package com.github.kervincandido.forum.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.kervincandido.forum.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private Long expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime extationDate = now.plus(expiration, ChronoField.MILLI_OF_DAY.getBaseUnit());
		
		return Jwts.builder()
				.setIssuer("Forum-api")
				.setSubject(user.getId().toString())
				.setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(extationDate.atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public boolean isValidToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token);
			return true;
		} catch (UnsupportedJwtException e) {
			return false;
		}
	}
	
	public Long getUserId(String token) throws UnsupportedJwtException {
		String userId = Jwts.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
		
		return Long.parseLong(userId);
	}
}
