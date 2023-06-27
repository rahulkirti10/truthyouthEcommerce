package com.truthyouth.commerce.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.truthyouth.commerce.dto.request.ProductCategoryRequestDto;
import com.truthyouth.commerce.dto.response.ResponseDto;
import com.truthyouth.commerce.entities.Admin;
import com.truthyouth.commerce.entities.ProductCategories;
import com.truthyouth.commerce.exception.GlobalException;
import com.truthyouth.commerce.repository.ProductCategoriesRepository;
import com.truthyouth.commerce.service.ProductService;
import com.truthyouth.commerce.utility.AppUtility;

@Service
@Transactional
public class productServiceImpl implements ProductService{
	
	@Autowired
	private ProductCategoriesRepository productCategoriesRepository;

	@Override
	public ResponseEntity<?> addProductCategory(ProductCategoryRequestDto categoryRequestDto) {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) {
			throw new GlobalException("User must be logged in!!");
		}
		ProductCategories categories = new ProductCategories();
		categories.setName(categoryRequestDto.getCategoryName());
		productCategoriesRepository.save(categories);
		
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully save product Category...");
        successResponseDto.setStatus("success");
        return ResponseEntity.ok(successResponseDto);
	}

	@Override
	public ResponseEntity<?> getAllProductCategory(int pageNo, int pageSize) {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) {
			throw new GlobalException("User must be logged in!!");
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<ProductCategories> categories = productCategoriesRepository.findAll(pageable);

		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully get product Category...");
        successResponseDto.setStatus("success");
        successResponseDto.setData(categories.getContent());
        return ResponseEntity.ok(successResponseDto);
	}

}
