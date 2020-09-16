package com.github.kervincandido.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.kervincandido.forum.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
