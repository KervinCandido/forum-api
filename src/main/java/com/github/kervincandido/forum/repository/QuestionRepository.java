package com.github.kervincandido.forum.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.github.kervincandido.forum.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> { }
