package com.answer.best.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.answer.best.entity.Questions;


public interface QuestionRepo extends JpaRepository<Questions,Integer> {
	@Query(value="select * from questions",nativeQuery=true)
    List<Questions> getAllQuestions();
	
	Questions findByQuestion(String question);
}
