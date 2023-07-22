package com.truthyouth.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truthyouth.commerce.dto.request.ProductBannersRequestDto;
import com.truthyouth.commerce.dto.request.ProductCategoryRequestDto;
import com.truthyouth.commerce.dto.request.ProductRequestDto;
import com.truthyouth.commerce.service.ProductService;

@RestController
@RequestMapping("/api/v1/admin")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/addProductCategory")
	public ResponseEntity<?> addProductCategory(@RequestBody ProductCategoryRequestDto categoryRequestDto){
		return productService.addProductCategory(categoryRequestDto);
	}
	
	@GetMapping("/getAllProductCategory")
	public ResponseEntity<?> getAllProductCategory(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		return productService.getAllProductCategory(pageNo, pageSize);
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@ModelAttribute ProductRequestDto productRequestDto){
		return productService.addProduct(productRequestDto);
	}
	
	@GetMapping("/getAllProduct")
	public ResponseEntity<?> getAllProduct(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		return productService.getAllProduct(pageNo, pageSize);
	}
	
	@PostMapping("/addProductBanner")
	public ResponseEntity<?> addProductBanner(@ModelAttribute ProductBannersRequestDto productBannersRequestDto){
		return productService.addProductBanner(productBannersRequestDto);
	}
	
	@PutMapping("/updateProductBanner")
	public ResponseEntity<?> updateProductBanner(@ModelAttribute ProductBannersRequestDto productBannersRequestDto){
		return productService.updateProductBanner(productBannersRequestDto);
	}
	
	@GetMapping("/getProductBanner")
	public ResponseEntity<?> getProductBanner(){
		return productService.getProductBanner();
	}
	
	@GetMapping("/getProductBannerById")
	public ResponseEntity<?> getProductBannerById(@RequestParam("id") long id){
		return productService.getProductBannerById(id);
	}

}
