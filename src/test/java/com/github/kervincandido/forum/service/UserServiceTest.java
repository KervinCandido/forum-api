package com.github.kervincandido.forum.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
	
	@MockBean
	private TokenService tokenService;
	
	@MockBean
	private TokenExtractorService tokenExtractor;

	private User user;	
	
	@Before
	public void init() {
		user = new User();
		user.setId(1L);
		user.setName("Test123");
		user.setUserName("test123");
		user.setEmail("test@email.com");
	}
	
	@Test
	public void deveEncontrarUsuarioPorId() {
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
		
		verify(userRepository).findById(ArgumentMatchers.any(Long.class));
	}
	
	@Test
	public void naoDeveEncontrarUsuarioPorId() {
		when(userRepository.findById(1L))
			.thenReturn(Optional.empty());
		
		Optional<User> optionalUser = service.findById(1L);
		assertThat(optionalUser, is(notNullValue())); 
		assertThat(optionalUser.isPresent(), is(false));
		verify(userRepository).findById(ArgumentMatchers.any(Long.class));
	}
	
	@Test
	public void deveEncontrarUsuarioPorEmail() {
		when(userRepository.findByEmail(user.getEmail()))
			.thenReturn(Optional.of(user));
		
		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
		assertThat(optionalUser, is(notNullValue()));
		assertThat(optionalUser.isPresent(), is(true));
		assertThat(optionalUser.get(), is(equalTo(user)));
		verify(userRepository).findByEmail(ArgumentMatchers.any(String.class));
	}
	
	@Test
	public void naoDeveEncontrarUsuarioPorEmail() {
		when(userRepository.findByEmail(user.getEmail()))
			.thenReturn(Optional.empty());
	
		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
		assertThat(optionalUser, is(notNullValue()));
		assertThat(optionalUser.isPresent(), is(false));
		verify(userRepository).findByEmail(ArgumentMatchers.any(String.class));
	}
	
	@Test
	public void deveDevolverUsuarioLogado() {
		when(tokenExtractor.extractRequestToken(ArgumentMatchers.any(HttpServletRequest.class)))
			.thenReturn("Token");
		
		when(tokenService.getUserId(ArgumentMatchers.any(String.class)))
			.thenReturn(1L);
		
		when(tokenService.isValidToken(ArgumentMatchers.any(String.class)))
			.thenReturn(true);
		
		when(userRepository.findById(1L))
			.thenReturn(Optional.of(user));
		
		Optional<User> optionalLoggedUser = service.getLoggedUser();
		assertThat(optionalLoggedUser, is(notNullValue())); 
		assertThat(optionalLoggedUser.isPresent(), is(true));
		
		verify(tokenExtractor).extractRequestToken(ArgumentMatchers.any(HttpServletRequest.class));
		verify(tokenService).isValidToken(ArgumentMatchers.any(String.class));
		verify(tokenService).getUserId(ArgumentMatchers.any(String.class));
		verify(userRepository).findById(ArgumentMatchers.any(Long.class));
	}
	
	@Test
	public void naoDevolveUsuarioSeNaoExistirToken() {
		when(tokenExtractor.extractRequestToken(ArgumentMatchers.any(HttpServletRequest.class)))
			.thenReturn(null);
		
		when(tokenService.isValidToken(ArgumentMatchers.any(String.class)))
			.thenReturn(false);
		
		Optional<User> optionalLoggedUser = service.getLoggedUser();
		assertThat(optionalLoggedUser, is(notNullValue())); 
		assertThat(optionalLoggedUser.isPresent(), is(false));
		
		verify(tokenExtractor).extractRequestToken(ArgumentMatchers.any(HttpServletRequest.class));
		verify(tokenService, times(0)).isValidToken(ArgumentMatchers.any(String.class));
		verify(tokenService, times(0)).getUserId(ArgumentMatchers.any(String.class));
		verify(userRepository, times(0)).findById(ArgumentMatchers.any(Long.class));
	}
	
	@Test
	public void naoDevolveUsuarioSeTokenForInvalido() {
		when(tokenExtractor.extractRequestToken(ArgumentMatchers.any(HttpServletRequest.class)))
			.thenReturn("Token invalido");
		
		when(tokenService.isValidToken(ArgumentMatchers.any(String.class)))
			.thenReturn(false);
		
		Optional<User> optionalLoggedUser = service.getLoggedUser();
		assertThat(optionalLoggedUser, is(notNullValue())); 
		assertThat(optionalLoggedUser.isPresent(), is(false));
		
		verify(tokenExtractor).extractRequestToken(ArgumentMatchers.any(HttpServletRequest.class));
		verify(tokenService, times(1)).isValidToken(ArgumentMatchers.any(String.class));
		verify(tokenService, times(0)).getUserId(ArgumentMatchers.any(String.class));
		verify(userRepository, times(0)).findById(ArgumentMatchers.any(Long.class));
	}
}
