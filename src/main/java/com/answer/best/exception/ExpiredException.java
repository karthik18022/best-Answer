package com.answer.best.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.answer.best.response.ResponseVo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExpiredException implements AuthenticationEntryPoint {

	@Autowired
	com.answer.best.dao.JwtTokenUtil tokenUtil;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		final ObjectMapper mapper = new ObjectMapper();
		final String token = (String) request.getAttribute("expired");
		System.out.println(token);
		if (token != null) {
			ResponseVo res = new ResponseVo();
			res.setCode(HttpServletResponse.SC_UNAUTHORIZED);
			res.setMessage("error : " + "token expired.access denied");
			res.setStatus("error");
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getOutputStream().println(mapper.writeValueAsString(res));
		} else {
			ResponseVo res = new ResponseVo();
			res.setCode(HttpServletResponse.SC_UNAUTHORIZED);
			res.setMessage("error : " + "access denied");
			res.setStatus("error");
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getOutputStream().println(mapper.writeValueAsString(res));
		}
	}
}
