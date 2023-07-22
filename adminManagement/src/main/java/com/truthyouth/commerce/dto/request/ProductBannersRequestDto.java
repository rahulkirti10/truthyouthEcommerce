package com.truthyouth.commerce.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductBannersRequestDto {
	
	private long id;

	private MultipartFile image;
	
	private String navigateUrl;
}
