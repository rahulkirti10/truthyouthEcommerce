package com.truthyouth.commerce.service;

import org.springframework.http.ResponseEntity;

import com.truthyouth.commerce.dto.request.AddToCartRequestDto;

public interface ProductService {

	ResponseEntity<?> homeSearch(String keyword);

	ResponseEntity<?> getProductByKeyword(String keyword, Integer pageNo);

	ResponseEntity<?> getProductById(long id);

	ResponseEntity<?> addToCart(AddToCartRequestDto addToCartRequestDto);
}
