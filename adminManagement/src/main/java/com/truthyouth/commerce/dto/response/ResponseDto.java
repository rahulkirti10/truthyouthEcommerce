package com.truthyouth.commerce.dto.response;

import lombok.Data;

@Data
public class ResponseDto {

	private String message; 
	
	private String status;
	
	private Object data;
}
