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
}
