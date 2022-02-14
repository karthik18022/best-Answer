package com.answer.best.response;

import javax.servlet.http.HttpServletResponse;

import com.answer.best.store.MessageStore;

public class BaseClass {

	public ResponseVo success(final ResponseVo responseVo, final Object response, final String message) {
		responseVo.setCode(HttpServletResponse.SC_OK);
		responseVo.setStatus(MessageStore.SUCCESS);
		responseVo.setMessage(message);
		responseVo.setResponse(response);
		return responseVo;
	}

}
