package com.truthyouth.commerce.dto.request;

import java.util.List;
import com.truthyouth.commerce.entities.ProductImages;
import com.truthyouth.commerce.entities.ProductsSizes;

import lombok.Data;

@Data
public class ProductRequestDto {
	
	private String name;
	
	private String discountedPrice;
	
	private String originalPrice;
	
	private String color;
	
	private String description;
	
	private String materialAndCare;
	
	private long productCategoryId;
	
	private List<ProductImagesDto> productImages;
	
	private List<ProductsSizesDto> productsSizes;
}
