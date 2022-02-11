package com.answer.best.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAnswerVo {
	private int questionId;
	private String question;
	private String userAnswer;
	private String answer;

}
