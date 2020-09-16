package com.github.kervincandido.forum.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static String parseToString(Object value) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(value);
	}
}
