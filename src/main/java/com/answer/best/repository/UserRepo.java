package com.answer.best.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.answer.best.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	@Query(value = "select user_id from users where email= :email", nativeQuery = true)
	int getUserId(@Param(value = "email") String email);

	User findByEmail(String email);

	User findByUserName(String username);
}
