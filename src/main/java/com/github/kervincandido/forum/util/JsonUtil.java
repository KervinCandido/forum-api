package com.github.kervincandido.forum.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static String parseToString(Object value) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(value);
	}
	
	public static <C> C parseToObject(String value, Class<C> clas) throws JsonMappingException, JsonProcessingException {
		return new ObjectMapper().readValue(value, clas);
	}
	
	public static Object parseToObject(String value) throws JsonMappingException, JsonProcessingException {
		return new ObjectMapper().readValue(value, Object.class);
	}
}
