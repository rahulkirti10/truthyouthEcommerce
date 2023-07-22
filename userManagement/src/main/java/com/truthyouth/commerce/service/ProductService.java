package com.truthyouth.commerce.service;

import org.springframework.http.ResponseEntity;

public interface ProductService {

	ResponseEntity<?> homeSearch(String keyword);
}
