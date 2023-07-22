package com.truthyouth.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truthyouth.commerce.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/homeSearch")
	public ResponseEntity<?> homeSearch(@RequestParam("keyword") String keyword){
		return productService.homeSearch(keyword);
	}
}
