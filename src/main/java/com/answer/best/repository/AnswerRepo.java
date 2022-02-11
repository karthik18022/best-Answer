package com.answer.best.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.answer.best.entity.UserAnswer;


public interface AnswerRepo extends JpaRepository<UserAnswer, Integer> {
	
}
