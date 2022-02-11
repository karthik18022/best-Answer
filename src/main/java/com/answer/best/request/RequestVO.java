package com.answer.best.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestVO {

	public String email;
	private String username;
	public String password;
	public List<QuestionRequest> request;
}
