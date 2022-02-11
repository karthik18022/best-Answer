package com.answer.best.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.answer.best.dao.JwtTokenUtil;
import com.answer.best.dao.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JwtTokenControllerTest {
	
	@MockBean
	UserService userService;
	
	@MockBean
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	 private MockMvc mvc;
	
	@Test
	public void tokenTest() throws Exception {
		String email="madhu@gmail.com";
		String password="password";
		
		UserDetails userDetails=userService.loadUserByUsername(email);
		String token=jwtTokenUtil.generateToken(userDetails);
		 assertNotNull(token);
	        mvc.perform(MockMvcRequestBuilders.get("/user/answers").header("Authorization", token)).andExpect(status().isOk());
	}

}
