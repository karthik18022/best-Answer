package com.answer.best.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.answer.best.model.JwtRequest;
import com.answer.best.model.JwtResponse;
import com.answer.best.repository.QuestionRepo;
import com.answer.best.repository.UserRepo;
import com.answer.best.request.QuestionRequest;
import com.answer.best.request.RequestVO;
import com.answer.best.response.QuestionResponse;
import com.answer.best.response.ResponseVo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {

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
	
	final  ObjectMapper mapper = new ObjectMapper();


	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getQuestion() throws Exception {
		List<QuestionResponse> list = new ArrayList<>();
		QuestionResponse question = new QuestionResponse();
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
		Questions question = new Questions();
		question.setQuestionId(11);
		question.setQuestion("test");
		question.setOptionA("test-1");
		question.setOptionB("test-2");
		question.setOptionC("test-3");
		question.setOptionD("test-4");
		question.setAnswer("answer");
		com.google.gson.Gson gson = new com.google.gson.Gson();
		String json = gson.toJson(question);
		MvcResult result = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	public void addAnswer() throws Exception {
		String url = "http://localhost:8080" + "/userAnswer";
		RequestVO req = new RequestVO();
		req.setEmail("test@gmail.com");
		req.setPassword("test");
		List<QuestionRequest> list = new ArrayList<>();
		QuestionRequest qRequest = new QuestionRequest();
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
		com.google.gson.Gson gson = new com.google.gson.Gson();
		String json = gson.toJson(req);
		MvcResult result = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	public void jwtTokenTest() throws Exception {
		JwtRequest request = new JwtRequest();
		request.setEmail("kanimozhi@gmail.com");
		request.setPassword("password");

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn();

		ResponseVo auctual = mapper.readValue(result.getResponse().getContentAsString(), ResponseVo.class);
//		String json = new ObjectMapper().writeValueAsString(auctual.getResponse());
		JsonNode jsonNode = mapper.readTree(new ObjectMapper().writeValueAsString(auctual.getResponse()));
		JwtResponse response = new JwtResponse();
		response.setJwttoken(jsonNode.get("jwttoken").asText());
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("kanimozhi@gmail.com");

		mvc.perform(MockMvcRequestBuilders.get("/user/answers").principal(mockPrincipal).header("Authorization",
				"Bearer " + response.getJwttoken())).andExpect(status().isOk());
		assertEquals("kanimozhi@gmail.com", mockPrincipal.getName());
	}
}
