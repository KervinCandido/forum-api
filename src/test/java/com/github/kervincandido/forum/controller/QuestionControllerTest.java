package com.github.kervincandido.forum.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.kervincandido.forum.controller.form.QuestionForm;
import com.github.kervincandido.forum.model.Question;
import com.github.kervincandido.forum.model.User;
import com.github.kervincandido.forum.service.QuestionService;
import com.github.kervincandido.forum.service.UserService;
import com.github.kervincandido.forum.util.JsonUtil;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@SpringBootTest
@ActiveProfiles("test")
public class QuestionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private QuestionService service;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void deveBuscarQuestionsEDevolver200() throws Exception {
		when(service.findAllQuestions(ArgumentMatchers.any(Pageable.class)))
			.thenReturn(Page.empty());
		
		mockMvc.perform(get("/question"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deveBuscarQuestionsEDevolverTresQuestions() throws Exception {
		User author = new User();
		author.setUserName("test123");
		
		LocalDateTime now = LocalDateTime.of(2020, 9, 9, 18, 00, 00);
		
		Question q1 = new Question();
		q1.setId(1L);
		q1.setAuthor(author);
		q1.setDescription("Dúvida de HTML");
		q1.setCreateDate(now);
		
		Question q2 = new Question();
		q2.setId(2L);
		q2.setAuthor(author);
		q2.setDescription("Dúvida de CSS");
		q2.setCreateDate(now.plusMinutes(1));
		
		Question q3 = new Question();
		q3.setId(3L);
		q3.setAuthor(author);
		q3.setDescription("Dúvida de JS");
		q3.setCreateDate(now.plusMinutes(2));
		
		Page<Question> page = new PageImpl<>(Arrays.asList(q1, q2, q3), PageRequest.of(0, 20), 3);
		
		when(service.findAllQuestions(ArgumentMatchers.any(Pageable.class)))
			.thenReturn(page);
		
		mockMvc.perform(get("/question"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(jsonPath("$.content", Matchers.hasSize(3)))
			.andExpect(jsonPath("$.content[0].id", is(equalTo(1))))
			.andExpect(jsonPath("$.content[0].description", is(equalTo("Dúvida de HTML"))))
			.andExpect(jsonPath("$.content[0].createDate", is(equalTo("2020-09-09 18:00:00"))))
			.andExpect(jsonPath("$.content[0].author", is(equalTo("test123"))))
			.andExpect(jsonPath("$.content[0].views", is(equalTo(0))))
			.andExpect(jsonPath("$.content[1].id", is(equalTo(2))))
			.andExpect(jsonPath("$.content[1].description", is(equalTo("Dúvida de CSS"))))
			.andExpect(jsonPath("$.content[1].createDate", is(equalTo("2020-09-09 18:01:00"))))
			.andExpect(jsonPath("$.content[1].author", is(equalTo("test123"))))
			.andExpect(jsonPath("$.content[1].views", is(equalTo(0))))
			.andExpect(jsonPath("$.content[2].id", is(equalTo(3))))
			.andExpect(jsonPath("$.content[2].description", is(equalTo("Dúvida de JS"))))
			.andExpect(jsonPath("$.content[2].createDate", is(equalTo("2020-09-09 18:02:00"))))
			.andExpect(jsonPath("$.content[2].author", is(equalTo("test123"))))
			.andExpect(jsonPath("$.content[2].views", is(equalTo(0))));
	}
	
	@Test
	public void deveCriarQuestaoEDevolverStatusCode201() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setUserName("test123");
		user.setEmail("test@email.com");
		user.setName("test123");
		user.setPassword("12345678");
	
		LocalDateTime now = LocalDateTime.now();
		
		Question question = new Question();
		question.setId(1L);
		question.setAuthor(user);
		question.setCreateDate(now);
		question.setDescription("Dúvida de Spring Boot");
		
		QuestionForm questionForm = new QuestionForm(); 
		questionForm.setDescription("Dúvida de Spring Boot");
		
		when(userService.getLoggedUser())
			.thenReturn(Optional.of(user));
		
		when(service.create(ArgumentMatchers.any(Question.class)))
			.thenReturn(question);
		
		mockMvc.perform(post("/question")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.parseToString(questionForm)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://*/question/1"));
		
		verify(userService).getLoggedUser();
		verify(service).create(ArgumentMatchers.any(Question.class));
	}
}
