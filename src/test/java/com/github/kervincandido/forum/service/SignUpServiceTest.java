package com.github.kervincandido.forum.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.kervincandido.forum.controller.form.SignUpForm;
import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignUpServiceTest {

	@Autowired
	private SignUpService service;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void deveCriarUmNovoUsuarioEDevolverComId() {
		SignUpForm userForm = new SignUpForm();
		userForm.setName("Teste123");
		userForm.setUserName("teste123");
		userForm.setEmail("teste@email.com");
		userForm.setPassword("secret");

		LocalDateTime now = LocalDateTime.now();
		
		User returnedUser = new User();
		returnedUser.setId(1L);
		returnedUser.setName("Test123");
		returnedUser.setUserName("test123");
		returnedUser.setEmail("teste@email.com");
		returnedUser.setActive(true);
		returnedUser.setCreateDate(now);
		returnedUser.setPassword("123456");
		
		when(userRepository.save(Mockito.any(User.class)))
			.thenReturn(returnedUser);
		
		User user = service.create(userForm);
		
		assertThat(user, is(notNullValue()));
		assertThat(user.getName(), equalTo("Test123"));
		assertThat(user.getUserName(), equalTo("test123"));
		assertThat(user.getEmail(), equalTo("teste@email.com"));
		assertThat(user.isActive(), is(true));
		assertThat(user.getCreateDate(), equalTo(now));
		assertThat(user.getPassword(), equalTo("123456"));
	}

	@Test
	public void naoDeveCriarUmNovoUsuarioComEmailJaExistente() {
		SignUpForm userForm = new SignUpForm();
		userForm.setName("Teste");
		userForm.setUserName("teste");
		userForm.setEmail("teste@teste.com");
		userForm.setPassword("secret");
		
		User returnedUser = new User();
		returnedUser.setEmail("teste@test.com");
		
		when(userRepository.findOptionalByEmail(userForm.getEmail()))
			.thenReturn(Optional.of(returnedUser));
		
		assertThrows(IllegalArgumentException.class, () -> service.create(userForm), "E-mail already registered");	
	}

}
