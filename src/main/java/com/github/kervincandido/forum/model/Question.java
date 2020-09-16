package com.github.kervincandido.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "create_date", nullable = false)
	private LocalDateTime createDate;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_author", nullable = false)
	private User author;
	
	@Column(name = "views", nullable = false)
	private Long views;
	
	@OneToMany(mappedBy = "question")
	private List<Answer> answers;
	
	public Question() {
		this.views = 0L;
		this.answers = new ArrayList<>();
		this.createDate = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	public List<Answer> getAnswers() {
		return new ArrayList<>(answers);
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = new ArrayList<Answer>(answers);
	}
}
