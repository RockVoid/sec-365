package com.feme.sec365.security.response;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
	private UUID userId;
	private String jwtToken;
	private String username;
	private List<String> roles;
	
	public UserInfoResponse(UUID userId, String jwtToken, String username, List<String> roles) {
		this.userId = userId;
		this.jwtToken = jwtToken;
		this.username = username;
		this.roles = roles;
	}
	
	public UserInfoResponse(UUID userId, String username, List<String> roles) {
		this.userId = userId;
		this.username = username;
		this.roles = roles;
	}
}
