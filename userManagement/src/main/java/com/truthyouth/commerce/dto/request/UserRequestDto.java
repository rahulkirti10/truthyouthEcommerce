package com.truthyouth.commerce.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {

	private String emailOrMobile;
	
	private String password;
}
