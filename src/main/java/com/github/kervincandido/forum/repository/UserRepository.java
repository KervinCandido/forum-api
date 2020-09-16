package com.github.kervincandido.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.kervincandido.forum.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findOptionalByEmail(String email);

	Optional<User> findByEmail(String email);
}
