package com.truthyouth.commerce.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class CartRepsonseDto {

	private long id;
	List<ProductResponseDto> productResponseDtos;
	
}
