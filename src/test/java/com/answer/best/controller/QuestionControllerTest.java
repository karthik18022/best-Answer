package com.answer.best.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.answer.best.dao.AnswerImpl;
import com.answer.best.dao.QuestionImpl;
import com.answer.best.entity.Questions;
import com.answer.best.repository.QuestionRepo;
import com.answer.best.repository.UserRepo;
import com.answer.best.request.QuestionRequest;
import com.answer.best.request.RequestVO;
import com.answer.best.response.QuestionResponse;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @InjectMocks
	QuestionController questionController;
	
	@MockBean
	QuestionImpl questionImpl;
	
	@MockBean
	AnswerImpl answerImpl;
	
	@Mock
	QuestionRepo questionRepo;
	
	@Mock
	UserRepo userRepo;

	@Autowired
	  private MockMvc mvc;
	
	 @Autowired
	  private WebApplicationContext webApplicationContext;
	 
	 @Before
	  public void setUp() {
	    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	  }
	
	@Test
	public void test() throws Exception {
		List<QuestionResponse> list=new ArrayList<>();
		QuestionResponse question=new QuestionResponse();
		question.setQuestionId(11);
		question.setQuestion("test");
		question.setOptionA("test-1");
		question.setOptionB("test-2");
		question.setOptionC("test-3");
		question.setOptionD("test-4");
		list.add(question);
		  Mockito.when(questionImpl.getQuestions()).thenReturn(list);
		  mvc.perform(get("/questions")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.aMapWithSize(4)));
//          .andExpect(jsonPath("$.response", Matchers.equalTo("questionId=11,question=test,optionA=test-1,optionB=test-2,optionC=test-3,optionD=test-4")));
//		List<QuestionResponse> questionList=questionImpl.getQuestions();
//		assertNotNull(questionList);
		}
	
	@Test
	public void addQuestion() throws Exception {
		String url = "http://localhost:8080" + "/question";
		Questions question=new Questions();
		question.setQuestionId(11);
		question.setQuestion("test");
		question.setOptionA("test-1");
		question.setOptionB("test-2");
		question.setOptionC("test-3");
		question.setOptionD("test-4");
		question.setAnswer("answer");
		 com.google.gson.Gson gson=new com.google.gson.Gson();
		 String json=gson.toJson(question);
	    MvcResult result = mvc.perform(
	            post(url)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isOk())
	            .andReturn();
	    Assert.assertNotNull(result);
//	    Mockito.verify(questionImpl,Mockito.times(1)).addQuestion();
	 
	}
	
	@Test
	public void addAnswer() throws Exception {
		String url = "http://localhost:8080" + "/userAnswer";
		RequestVO req=new RequestVO();
		req.setEmail("test@gmail.com");
		req.setPassword("test");
	    List<QuestionRequest> list=new ArrayList<>();
	    QuestionRequest qRequest=new QuestionRequest();
	    qRequest.setQuestionId(1);
	    qRequest.setUserAnswer("gandhi");
	    list.add(qRequest);
	    qRequest.setQuestionId(92);
	    qRequest.setUserAnswer("sachin");
	    list.add(qRequest);
	    qRequest.setQuestionId(93);
	    qRequest.setUserAnswer("cpu");
	    list.add(qRequest);
	    req.setRequest(list);
	    req.setUsername("test");
	    com.google.gson.Gson gson=new com.google.gson.Gson();
		 String json=gson.toJson(req);
		 MvcResult result = mvc.perform(
		            post(url)
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(json))
		            .andExpect(status().isOk())
		            .andReturn();
		    Assert.assertNotNull(result);
//	    answerImpl.postAnswer(req);
//	    Mockito.verify(answerImpl,Mockito.times(1)).postAnswer(req);
//	    Assert.assertNotNull(req);
	}
	
	@Test
	public void existentUserCanGetTokenAndAuthentication() throws Exception {
	    String email = "kanimozhi@gmail.com";
	    String password = "password";

	    String body = "{\"email\":\"" + email + "\", \"password\":\""+ password + "\"}";

	    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
	            .content(body))
	            .andExpect(status().isOk()).andReturn();

	    String response = result.getResponse().getContentAsString();
	    response = response.replace("{\"jwttoken\": \"", "");
	    String token = response.replace("\"}", "");

	    mvc.perform(MockMvcRequestBuilders.get("/user/answers")
	        .header("Authorization", "Bearer " + token))
	        .andExpect(status().isOk());
	}
}
