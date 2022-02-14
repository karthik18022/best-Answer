package com.answer.best.response;

public class AuthenticationResponse {

	private String refreshToken;

	public String getJwt() {
		return refreshToken;
	}

	public void setJwt(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public AuthenticationResponse(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
