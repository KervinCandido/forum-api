package com.github.kervincandido.forum.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.service.UserService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

	@Test
	public void deveDevolverUsuarioBuscadoPorId() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("Test123");
		user.setUserName("test123");
		user.setEmail("test@email.com");
		
		
		when(userService.findById(1L))
			.thenReturn(Optional.of(user));
		
		mockMvc.perform(get("/user/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("Test123")))
			.andExpect(jsonPath("$.userName", is("test123")))
			.andExpect(jsonPath("$.email", is("test@email.com")));
	}
	
	@Test
	public void naoDeveEncontraUsarioPorIdEDevolver404() throws Exception {
		
		when(userService.findById(1L))
			.thenReturn(Optional.empty());
		
		mockMvc.perform(get("/user/1"))
			.andExpect(status().isNotFound());
	}
}
