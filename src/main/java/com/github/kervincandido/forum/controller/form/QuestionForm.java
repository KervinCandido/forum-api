package com.github.kervincandido.forum.controller.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.github.kervincandido.forum.model.Question;

public class QuestionForm {

	@NotBlank
	@Length(min = 8, max=255)
	private String description;
	
	public QuestionForm() {
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Question toQuestion() {
		Question question = new Question();
		question.setDescription(this.description.trim());
		return question;
	}

	@Override
	public String toString() {
		return "QuestionForm [description=" + description + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionForm other = (QuestionForm) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}
}
