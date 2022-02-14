package com.answer.best.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreAndAnswerVO {

	private int score;
	private int userId;
	private List<UserAnswerVo> userAnswerVo;

}
