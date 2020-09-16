package com.github.kervincandido.forum.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.kervincandido.forum.controller.form.SignUpForm;
import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.service.SignUpService;
import com.github.kervincandido.forum.util.JsonUtil;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class SignUpControleTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SignUpService userService;
	
	@Test
	public void deveCriarUsuarioEDevolver201() throws Exception {
		
		SignUpForm userForm = new SignUpForm();
		userForm.setName("Test123");
		userForm.setUserName("test123");
		userForm.setEmail("test@email.com");
		userForm.setPassword("12345678");
		
		User user = new User();
		user.setId(1L);
		user.setName("Test123");
		user.setUserName("test123");
		user.setEmail("test@email.com");
		
		
		when(userService.create(userForm))
			.thenReturn(user);
			
		mockMvc.perform(post("/signup")
				.content(JsonUtil.parseToString(userForm))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://*/user/"+user.getId()))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Test123")))
		.andExpect(jsonPath("$.userName", is("test123")))
		.andExpect(jsonPath("$.email", is("test@email.com")));
	}
	
	@Test
	public void deveDarErroDeEmailJaCadastradoERetornar400() throws Exception {
		
		SignUpForm singUpForm = new SignUpForm();
		singUpForm.setName("Test123");
		singUpForm.setUserName("test123");
		singUpForm.setEmail("test@email.com");
		singUpForm.setPassword("12345678");
		
		when(userService.create(singUpForm))
		.thenThrow(IllegalArgumentException.class);
		
		mockMvc.perform(post("/signup")
				.content(JsonUtil.parseToString(singUpForm))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deveDarErroDeValidacaoERetornar400() throws Exception {
		SignUpForm signUpForm = new SignUpForm();
		signUpForm.setName("Test");
		signUpForm.setUserName("test123");
		signUpForm.setEmail("test@email.com");
		signUpForm.setPassword("12345678");
		
		mockMvc.perform(post("/signup")
				.content(JsonUtil.parseToString(signUpForm))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.[0].field", is("name")));
	}
}
