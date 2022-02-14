package com.answer.best.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseVo {
	private int code;
	private String status;
	private String message;
	private Object response;

	public ResponseVo(int code, String status, String message, Object response) {
		super();
		this.code = code;
		this.status = status;
		this.message = message;
		this.response = response;
	}

	public ResponseVo() {
		// TODO Auto-generated constructor stub
	}

}
