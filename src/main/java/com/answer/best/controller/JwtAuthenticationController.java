package com.answer.best.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.answer.best.dao.UserService;
import com.answer.best.model.JwtRequest;
import com.answer.best.response.BaseClass;
import com.answer.best.response.ResponseVo;
import com.answer.best.store.EndPointStore;
import com.answer.best.store.MessageStore;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
public class JwtAuthenticationController extends BaseClass {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	com.answer.best.dao.JwtTokenUtil jwtTokenUtil;

	@GetMapping("/")
	public String check() {
		return "welcome";
	}

	@RequestMapping(value = EndPointStore.AUTHENTICATE, method = RequestMethod.POST)
	public ResponseVo createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);
		final ResponseVo responseDao = new ResponseVo();
		return super.success(responseDao, new com.answer.best.model.JwtResponse(token), MessageStore.GET_TOKEN);
	}

	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseVo refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		final ResponseVo responseDao = new ResponseVo();
		return super.success(responseDao, new com.answer.best.model.JwtResponse(token), MessageStore.GET_REFRESH_TOKEN);
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (java.util.Map.Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
