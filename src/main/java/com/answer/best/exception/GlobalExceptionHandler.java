package com.answer.best.exception;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.answer.best.response.ResponseVo;
import com.answer.best.store.MessageStore;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> userNameException(String message) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setCode(HttpServletResponse.SC_BAD_REQUEST);
		responseVo.setStatus(MessageStore.FAILURE);
		responseVo.setMessage(MessageStore.EMAIL_NOT_FOUND_EXCEPTION);
		return new ResponseEntity(responseVo, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmailFoundException.class)
	public ResponseEntity<?> validException(Exception exception, WebRequest request) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setCode(HttpServletResponse.SC_EXPECTATION_FAILED);
		responseVo.setStatus(MessageStore.FAILURE);
		responseVo.setMessage(exception.getMessage());
		return new ResponseEntity(responseVo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> validException1(Exception exception, WebRequest request) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setCode(HttpServletResponse.SC_BAD_REQUEST);
		responseVo.setStatus(MessageStore.FAILURE);
		responseVo.setMessage("email not valid");
		return new ResponseEntity(responseVo, HttpStatus.BAD_REQUEST);
	}

}
