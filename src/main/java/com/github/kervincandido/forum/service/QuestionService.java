package com.github.kervincandido.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.kervincandido.forum.model.Question;
import com.github.kervincandido.forum.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository repository;

	public Question create(Question questionForm) {
		return repository.save(questionForm);
	}

	public Page<Question> findAllQuestions(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
