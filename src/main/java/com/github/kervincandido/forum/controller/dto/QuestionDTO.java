package com.github.kervincandido.forum.controller.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.kervincandido.forum.model.Question;

public class QuestionDTO {
	
	private Long id;
	private String description;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;
	
	private String author;
	private Long views;
	
	public QuestionDTO(Question question) {
		this.id = question.getId();
		this.description = question.getDescription();
		this.createDate = question.getCreateDate();
		this.author = question.getAuthor().getUserName();
		this.views = question.getViews();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public String getAuthor() {
		return author;
	}

	public Long getViews() {
		return views;
	}

	public static Page<QuestionDTO> fromPageQuestion(Page<Question> questions) {
		return questions.map(QuestionDTO::new);
	}
}
