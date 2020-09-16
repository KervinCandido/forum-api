package com.github.kervincandido.forum.controller.dto;

public class TokenDTO {

	private final String token;
	private final String type;

	public TokenDTO(String token, String type) {
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}
}
