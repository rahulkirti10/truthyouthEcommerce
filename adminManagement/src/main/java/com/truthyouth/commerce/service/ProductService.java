package com.truthyouth.commerce.service;

import org.springframework.http.ResponseEntity;

import com.truthyouth.commerce.dto.request.ProductCategoryRequestDto;

public interface ProductService {

	ResponseEntity<?> addProductCategory(ProductCategoryRequestDto categoryRequestDto);

	ResponseEntity<?> getAllProductCategory(int pageNo, int pageSize);

}
