package com.bbots.mfin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {

	private String message;

	@JsonProperty("mother_token")
	private String motherToken;
	private String refreshToken;
	


	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	private LoginResponseDto user;

	public String getMotherToken() {
		return motherToken;
	}

	public void setMotherToken(String motherToken) {
		this.motherToken = motherToken;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoginResponseDto getUser() {
		return user;
	}

	public void setUser(LoginResponseDto user) {
		this.user = user;
	}

	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}