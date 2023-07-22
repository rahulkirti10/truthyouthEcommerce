package com.truthyouth.commerce.service;

import org.springframework.http.ResponseEntity;

import com.truthyouth.commerce.dto.request.ProductBannersRequestDto;
import com.truthyouth.commerce.dto.request.ProductCategoryRequestDto;
import com.truthyouth.commerce.dto.request.ProductRequestDto;

public interface ProductService {

	ResponseEntity<?> addProductCategory(ProductCategoryRequestDto categoryRequestDto);

	ResponseEntity<?> getAllProductCategory(int pageNo, int pageSize);

	ResponseEntity<?> addProduct(ProductRequestDto productRequestDto);

	ResponseEntity<?> getAllProduct(int pageNo, int pageSize);

	ResponseEntity<?> addProductBanner(ProductBannersRequestDto productBannersRequestDto);

	ResponseEntity<?> getProductBanner();

	ResponseEntity<?> updateProductBanner(ProductBannersRequestDto productBannersRequestDto);

	ResponseEntity<?> getProductBannerById(long id);

}
