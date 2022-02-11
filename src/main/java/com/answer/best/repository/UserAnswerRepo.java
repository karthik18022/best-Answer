package com.answer.best.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.answer.best.entity.UserAnswer;

public interface UserAnswerRepo extends JpaRepository<UserAnswer, Integer>{
	@Query(value="select ua.question_id ,q.question,ua.user_id,ua.answer_id,ua.answer,q.answer from user_answer ua left  join questions q on ua.question_id =q.question_id where ua.user_id =:userId",nativeQuery=true)
	List<UserAnswer> getUserAns(@Param(value="userId") int userId);
}
