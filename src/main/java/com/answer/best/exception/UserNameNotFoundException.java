package com.answer.best.exception;

public class UserNameNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNameNotFoundException(String message) {
		super(message);
	}
}
