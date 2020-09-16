package com.github.kervincandido.forum.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.kervincandido.forum.controller.dto.QuestionDTO;
import com.github.kervincandido.forum.controller.form.QuestionForm;
import com.github.kervincandido.forum.model.Question;
import com.github.kervincandido.forum.service.QuestionService;
import com.github.kervincandido.forum.service.UserService;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService service;
	
	@Autowired
	private UserService userService;
		
	@GetMapping()
	public Page<QuestionDTO> findAllQuestions(
			@PageableDefault(page = 0, size = 20, direction = Direction.DESC, sort = "createDate") Pageable pageable) {
		Page<Question> questions = service.findAllQuestions(pageable);
		return QuestionDTO.fromPageQuestion(questions);
	}
	
	@PostMapping
	public ResponseEntity<QuestionDTO> createQuestion(@RequestBody @Valid QuestionForm questionForm,
			UriComponentsBuilder uriComponentsBuilder) {
		
		Question question = questionForm.toQuestion();
		question.setAuthor(userService.getLoggedUser()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)));

		question = service.create(question);
		
		URI uri = uriComponentsBuilder.path("/question/{id}").build(question.getId());
		return ResponseEntity.created(uri).build();
	}
}
