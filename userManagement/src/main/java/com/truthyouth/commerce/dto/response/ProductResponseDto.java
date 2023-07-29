package com.truthyouth.commerce.dto.response;

import java.util.List;

import com.truthyouth.commerce.dto.request.ProductCategoriesResponseDto;

import lombok.Data;

@Data
public class ProductResponseDto {

private String name;
	
	private String discountedPrice;
	
	private String originalPrice;
	
	private String color;
	
	private String description;
	
	private String materialAndCare;
	
	private String frontImageUrl;
	
	private long id;
	
	private List<ProductImagesResponseDto> imagesResponseDtos;
	
	private List<ProductSizesResponseDto> productSizesResponseDtos;
	
	private List<ProductCategoriesResponseDto> productCategoriesResponseDtos;
	
}
