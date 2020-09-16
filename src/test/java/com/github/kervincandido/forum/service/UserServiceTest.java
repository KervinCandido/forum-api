package com.github.kervincandido.forum.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService service;
	
	@MockBean
	private UserRepository userRepository;	
	
	@Test
	public void deveEncontrarUsuarioPorId() {
		User user = new User();
		user.setId(1L);
		user.setName("Test123");
		user.setUserName("test123");
		user.setEmail("test@email.com");
		
		when(userRepository.findById(1L))
			.thenReturn(Optional.of(user));
		
		Optional<User> optionalUser = service.findById(1L);
		assertThat(optionalUser, is(notNullValue()));
		assertThat(optionalUser.isPresent(), is(true));
		
		User userBanco = optionalUser.get();
		
		assertThat(userBanco.getId(), is(1L));
		assertThat(userBanco.getName(), equalTo("Test123"));
		assertThat(userBanco.getUserName(), equalTo("test123"));
		assertThat(userBanco.getEmail(), equalTo("test@email.com"));
	}
	
	@Test
	public void naoDeveEncontrarUsuarioPorId() {
		when(userRepository.findById(1L))
			.thenReturn(Optional.empty());
		
		Optional<User> optionalUser = service.findById(1L);
		assertThat(optionalUser, is(notNullValue())); 
		assertThat(optionalUser.isPresent(), is(false));
	}
}
