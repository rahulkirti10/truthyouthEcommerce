package com.truthyouth.commerce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.truthyouth.commerce.dto.response.ProductResponseDto;
import com.truthyouth.commerce.dto.response.ProductSizesResponseDto;
import com.truthyouth.commerce.dto.response.ResponseDto;
import com.truthyouth.commerce.entities.Products;
import com.truthyouth.commerce.entities.ProductsSizes;
import com.truthyouth.commerce.entities.SearchKeywords;
import com.truthyouth.commerce.repository.ProductsRepository;
import com.truthyouth.commerce.repository.SearchKeywordsRepository;
import com.truthyouth.commerce.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

	@Autowired
	private SearchKeywordsRepository searchKeywordsRepository;
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Override
	public ResponseEntity<?> homeSearch(String keyword) {
	
		keyword = "%" + keyword + "%";
		List<SearchKeywords> keywords = searchKeywordsRepository.findByKeyword(keyword);
		List<Map<String, Object>> list = new ArrayList<>();
		for(SearchKeywords searchKeywords : keywords) {
			//int countProduct = productsRepository.findByKeyword("%" + searchKeywords.getKeywords() + "%");
			Map<String, Object> map = new HashMap<>();
			map.put("title", searchKeywords.getKeywords());
			//map.put("count", countProduct);
			list.add(map);
		}
		
//		ResponseDto successResponseDto = new ResponseDto();
//		successResponseDto.setMessage("Successfully get search.");
//		successResponseDto.setStatus("success");
//		successResponseDto.setData(list);
		return ResponseEntity.ok(list);
	}

	@Override
	public ResponseEntity<?> getProductByKeyword(String keyword, Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo, 50);
		keyword = "%" + keyword + "%";
		Page<Products> products = productsRepository.findByKeywordAllProduct(keyword, pageable);
		List<ProductResponseDto> list = new ArrayList<>();
		for(Products product : products.getContent()) {
			ProductResponseDto productResponseDto = new ProductResponseDto();
			productResponseDto.setDescription(product.getColor());
			productResponseDto.setDescription(product.getDescription());
			productResponseDto.setDiscountedPrice(product.getDiscountedPrice());
			productResponseDto.setMaterialAndCare(product.getMaterialAndCare());
			productResponseDto.setName(product.getName());
			productResponseDto.setOriginalPrice(product.getOriginalPrice());
			productResponseDto.setFrontImageUrl(product.getFrontImageUrl());
			list.add(productResponseDto);;
			}
		ResponseDto successResponseDto = new ResponseDto();
		successResponseDto.setMessage("Successfully get all product.");
		successResponseDto.setStatus("success");
		successResponseDto.setData(list);
		return ResponseEntity.ok(successResponseDto);
	}

	
}
