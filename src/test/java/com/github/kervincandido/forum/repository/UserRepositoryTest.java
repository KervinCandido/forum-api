package com.github.kervincandido.forum.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.kervincandido.forum.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	public void naoDeveEncontrarPeloEmail() {
		Optional<User> optionalUser = repository.findOptionalByEmail("invalid@email.com");
		assertThat(optionalUser.isPresent(), is(false));
	}

	@Test
	public void deveDeveEncontrarPeloEmail() {
		String email = "userteste@teste.com";
		
		User user = new User();
		user.setUserName("teste");
		user.setName("teste");
		user.setEmail(email);
		user.setPassword("123");
		user.setActive(true);
		user.setCreateDate(LocalDateTime.now());
		
		em.persist(user);
		
		Optional<User> optionalUser = repository.findOptionalByEmail(email);
		assertThat(optionalUser.isPresent(), is(true));
		assertThat(optionalUser.get().getEmail(), is(equalTo(email)));
	}
}
