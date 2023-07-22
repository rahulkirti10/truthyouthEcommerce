package com.truthyouth.commerce.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductImagesDto {

	private MultipartFile images;
}
