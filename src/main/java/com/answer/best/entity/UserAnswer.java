package com.answer.best.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_answer")
@Getter
@Setter
public class UserAnswer {

	@Id
	@Column(name = "Answer_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer answerId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "question_id", referencedColumnName = "question_id")
	private Questions question;

	@Column(name = "answer", nullable = false)
	private String answer;

}
