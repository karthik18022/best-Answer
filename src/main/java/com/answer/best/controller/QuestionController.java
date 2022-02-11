package com.answer.best.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.answer.best.dao.AnswerImpl;
import com.answer.best.dao.QuestionImpl;
import com.answer.best.entity.Questions;
import com.answer.best.request.RequestVO;
import com.answer.best.response.BaseClass;
import com.answer.best.response.ResponseVo;
import com.answer.best.store.EndPointStore;
import com.answer.best.store.MessageStore;

@RestController
public class QuestionController extends BaseClass {

	@Autowired
	QuestionImpl questionImpl;

	@Autowired
	AnswerImpl answerImpl;

	@RequestMapping(value = EndPointStore.ADD_QUESTION, method = RequestMethod.POST)
	public ResponseVo addQuestion(@RequestBody Questions questions) {
		final ResponseVo responseDao = new ResponseVo();
		return super.success(responseDao, this.questionImpl.addQuestion(questions), MessageStore.POST_QUESTION);
	}

	@RequestMapping(value = EndPointStore.GET_ALL_QUESTIONS, method = RequestMethod.GET)
	public ResponseVo getAllQuestions() {
		final ResponseVo responseDao = new ResponseVo();
		return super.success(responseDao, this.questionImpl.getQuestions(), MessageStore.ALL_QUESTIONS);
	}

	@RequestMapping(value = EndPointStore.SAVE_USERANSWER, method = RequestMethod.POST)
	public ResponseVo postAnswer(@RequestBody RequestVO request) {
		final ResponseVo responseDao = new ResponseVo();
		return super.success(responseDao, this.answerImpl.postAnswer(request), MessageStore.POST_ANSWER);
	}

	@RequestMapping(value = EndPointStore.GET_USERANSWER, method = RequestMethod.GET)
	public ResponseVo getAnswers(Principal principal) {
		final ResponseVo responseDao = new ResponseVo();
		return super.success(responseDao, this.answerImpl.getAllAnswer(principal.getName()), MessageStore.USER_ANSWER);
	}

}
