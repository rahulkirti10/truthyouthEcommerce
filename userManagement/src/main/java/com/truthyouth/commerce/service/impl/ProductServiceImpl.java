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

import com.truthyouth.commerce.dto.request.AddToCartRequestDto;
import com.truthyouth.commerce.dto.response.ProductResponseDto;
import com.truthyouth.commerce.dto.response.ProductSizesResponseDto;
import com.truthyouth.commerce.dto.response.ResponseDto;
import com.truthyouth.commerce.entities.Admin;
import com.truthyouth.commerce.entities.Cart;
import com.truthyouth.commerce.entities.Products;
import com.truthyouth.commerce.entities.ProductsSizes;
import com.truthyouth.commerce.entities.SearchKeywords;
import com.truthyouth.commerce.entities.User;
import com.truthyouth.commerce.exception.GlobalException;
import com.truthyouth.commerce.repository.CartRepository;
import com.truthyouth.commerce.repository.ProductsRepository;
import com.truthyouth.commerce.repository.SearchKeywordsRepository;
import com.truthyouth.commerce.repository.UserRepository;
import com.truthyouth.commerce.service.ProductService;
import com.truthyouth.commerce.utility.AppUtility;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

	@Autowired
	private SearchKeywordsRepository searchKeywordsRepository;
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public ResponseEntity<?> homeSearch(String keyword) {
	
		keyword = "%" + keyword + "%";
		List<SearchKeywords> keywords = searchKeywordsRepository.findByKeyword(keyword);
		List<Map<String, Object>> list = new ArrayList<>();
//		for(SearchKeywords searchKeywords : keywords) {
//			//int countProduct = productsRepository.findByKeyword("%" + searchKeywords.getKeywords() + "%");
//			Map<String, Object> map = new HashMap<>();
//			map.put("title", searchKeywords.getKeywords());
//			//map.put("count", countProduct);
//			list.add(map);
//		}
		return ResponseEntity.ok(keywords);
	}

	@Override
	public ResponseEntity<?> getProductByKeyword(String keyword, Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo, 50);
		keyword = "%" + keyword + "%";
		if(keyword.equalsIgnoreCase("%all%")) {
			Page<Products> products = productsRepository.findAll(pageable);
			List<ProductResponseDto> list = new ArrayList<>();
			for(Products product : products.getContent()) {
				ProductResponseDto productResponseDto = new ProductResponseDto();
				productResponseDto.setColor(product.getColor());
				productResponseDto.setDescription(product.getDescription());
				productResponseDto.setDiscountedPrice(product.getDiscountedPrice());
				productResponseDto.setMaterialAndCare(product.getMaterialAndCare());
				productResponseDto.setName(product.getName());
				productResponseDto.setOriginalPrice(product.getOriginalPrice());
				productResponseDto.setFrontImageUrl(product.getFrontImageUrl());
				productResponseDto.setId(product.getId());
				list.add(productResponseDto);;
				}
			ResponseDto successResponseDto = new ResponseDto();
			successResponseDto.setMessage("Successfully get all product.");
			successResponseDto.setStatus("success");
			successResponseDto.setData(list);
			return ResponseEntity.ok(successResponseDto);
		}
		else {
			Page<Products> products = productsRepository.findByKeywordAllProduct(keyword, pageable);
			List<ProductResponseDto> list = new ArrayList<>();
			for(Products product : products.getContent()) {
				ProductResponseDto productResponseDto = new ProductResponseDto();
				productResponseDto.setColor(product.getColor());
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
	
	@Override
	public ResponseEntity<?> getProductById(long id) {
		
		Products categories = productsRepository.findById(id).orElse(null);

		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully get product...");
        successResponseDto.setStatus("success");
        successResponseDto.setData(categories);
        return ResponseEntity.ok(successResponseDto);
	}

	@Override
	public ResponseEntity<?> addToCart(AddToCartRequestDto addToCartRequestDto) {
		User user = AppUtility.getCurrentUser();
		
		if(user == null)
			throw new GlobalException("User not loggedIn.");
		
		Products products = productsRepository.findById(addToCartRequestDto.getProductId()).orElse(null);
		
		if(products == null)
			throw new GlobalException("Product not found.");
		
		Cart cart = cartRepository.findByUserAndProducts(user, products);
		
		if(cart != null)
			throw new GlobalException("Product already added in Cart.");
		
		cart = new Cart();
		cart.setProducts(products);
		cart.setUser(user);
		cartRepository.save(cart);
		
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully save product in cart...");
        successResponseDto.setStatus("success");
        return ResponseEntity.ok(successResponseDto);
	}

	@Override
	public ResponseEntity<?> getCartDetails() {
		User user = AppUtility.getCurrentUser();
		
		if(user == null)
			throw new GlobalException("User not loggedIn.");
		
		List<Cart> cart = cartRepository.findByUser(user);
		
		List<Map<String, Object>> list = new ArrayList<>();
		for(Cart c : cart) {
			Map<String, Object> map = new HashMap<>();
			Products product = c.getProducts();
			ProductResponseDto productResponseDto = new ProductResponseDto();
			productResponseDto.setColor(product.getColor());
			productResponseDto.setDescription(product.getDescription());
			productResponseDto.setDiscountedPrice(product.getDiscountedPrice());
			productResponseDto.setMaterialAndCare(product.getMaterialAndCare());
			productResponseDto.setName(product.getName());
			productResponseDto.setOriginalPrice(product.getOriginalPrice());
			productResponseDto.setFrontImageUrl(product.getFrontImageUrl());
			map.put("product", productResponseDto);
			map.put("id", c.getId());
			map.put("quantity", c.getQuantity());
			list.add(map);
		}
			
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully get cart details...");
        successResponseDto.setStatus("success");
        successResponseDto.setData(list);
        return ResponseEntity.ok(successResponseDto);
	}

	
}
