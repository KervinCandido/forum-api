package com.github.kervincandido.forum.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.github.kervincandido.forum.configuration.security.service.AuthService;
import com.github.kervincandido.forum.controller.form.SignInForm;
import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.repository.UserRepository;
import com.github.kervincandido.forum.util.JsonUtil;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@SpringBootTest
@ActiveProfiles({"dev"})
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthService authService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void init() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		User user = new User();
		user.setEmail("test@email.com");
		user.setPassword(encoder.encode("12345678"));
		user.setUserName("test123");
		user.setName("test123");
		
		user = userRepository.save(user);
		
		when(authService.loadUserByUsername(user.getEmail()))
			.thenReturn(user);
	}
	
	@Test
	public void deveAutenticarEDevover200() throws Exception {
		SignInForm signInForm = new SignInForm();
		signInForm.setEmail("test@email.com");
		signInForm.setPassword("12345678");
		
		mockMvc.perform(post("/auth")
				.content(JsonUtil.parseToString(signInForm))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveAutenticarEDevoverToken() throws Exception {
		
		SignInForm signInForm = new SignInForm();
		signInForm.setEmail("test@email.com");
		signInForm.setPassword("12345678");
		
		mockMvc.perform(post("/auth")
				.content(JsonUtil.parseToString(signInForm))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.token", is(notNullValue())));
	}
	
	@Test
	public void deveFalharAoAutenticarEDevoverUnathorized() throws Exception {

		SignInForm signInForm = new SignInForm();
		signInForm.setEmail("test@email.com");
		signInForm.setPassword("87654321");
		
		mockMvc.perform(post("/auth")
				.content(JsonUtil.parseToString(signInForm))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnauthorized());
	}
}
