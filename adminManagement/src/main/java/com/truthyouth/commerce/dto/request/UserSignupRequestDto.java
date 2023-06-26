package com.truthyouth.commerce.dto.request;

import lombok.Data;

@Data
public class UserSignupRequestDto {
	
	private String mobileNo;
	
	private String authToken;
	
	private String fullName;
	
	private String otp;
	
	private String email;
	
	private String password;
	
}
