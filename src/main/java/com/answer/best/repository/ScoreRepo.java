package com.answer.best.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.answer.best.entity.Score;
import com.answer.best.entity.User;
import java.util.List;

@Repository
public interface ScoreRepo extends JpaRepository<Score, Integer> {

	@Query(value = "select sum( case when q.answer =ua.answer then 1 else 0 end )as score from user_answer ua left join questions q on ua.question_id = q.question_id  where ua.user_id =:userId", nativeQuery = true)
	int getScore(@Param(value = "userId") int userId);

	@Query(value = "select score from scores where user_id=:userId", nativeQuery = true)
	int score(@Param(value = "userId") int userId);

}
