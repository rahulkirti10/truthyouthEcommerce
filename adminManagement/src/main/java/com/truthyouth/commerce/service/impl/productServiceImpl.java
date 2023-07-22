package com.truthyouth.commerce.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.truthyouth.commerce.config.AmazonClient;
import com.truthyouth.commerce.dto.request.ProductBannersRequestDto;
import com.truthyouth.commerce.dto.request.ProductCategoryRequestDto;
import com.truthyouth.commerce.dto.request.ProductImagesDto;
import com.truthyouth.commerce.dto.request.ProductRequestDto;
import com.truthyouth.commerce.dto.request.ProductsSizesDto;
import com.truthyouth.commerce.dto.response.ResponseDto;
import com.truthyouth.commerce.entities.Admin;
import com.truthyouth.commerce.entities.ProductBanners;
import com.truthyouth.commerce.entities.ProductCategories;
import com.truthyouth.commerce.entities.ProductImages;
import com.truthyouth.commerce.entities.Products;
import com.truthyouth.commerce.entities.ProductsSizes;
import com.truthyouth.commerce.exception.GlobalException;
import com.truthyouth.commerce.repository.ProductBannersRepository;
import com.truthyouth.commerce.repository.ProductCategoriesRepository;
import com.truthyouth.commerce.repository.ProductSizesRepository;
import com.truthyouth.commerce.repository.ProductsImagesRepository;
import com.truthyouth.commerce.repository.ProductsRepository;
import com.truthyouth.commerce.service.ProductService;
import com.truthyouth.commerce.utility.AppUtility;

@Service
@Transactional
public class productServiceImpl implements ProductService{
	
	@Autowired
	private ProductCategoriesRepository productCategoriesRepository;
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private ProductsImagesRepository productsImagesRepository;
	
	@Autowired
	private ProductSizesRepository productSizesRepository;
	
	@Autowired
	private ProductBannersRepository productBannersRepository;
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Value("${aws.s3.folder}")
	private String folder;
	
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

	@Override
	public ResponseEntity<?> addProduct(ProductRequestDto productRequestDto) {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) 
			throw new GlobalException("User must be logged in!!");
		
		ProductCategories categories = productCategoriesRepository.findById(productRequestDto.getProductCategoryId()).orElse(null);
		if(categories == null)
			throw new GlobalException("Category id not found!!");
		
		Products products = new Products();
		products.setColor(productRequestDto.getColor());
		products.setDescription(productRequestDto.getDescription());
		products.setDiscountedPrice(productRequestDto.getDiscountedPrice());
		products.setMaterialAndCare(productRequestDto.getMaterialAndCare());
		products.setName(productRequestDto.getName());
		products.setOriginalPrice(productRequestDto.getOriginalPrice());
		products.setProductCategories(categories);
		productsRepository.save(products);
		
		for(ProductImagesDto imagesDto : productRequestDto.getProductImages()) {
			ProductImages productImages = new ProductImages();
			String imageUrl = amazonClient.uploadFile(imagesDto.getImages(), folder);
			productImages.setImageUrl(imageUrl);
			productImages.setProducts(products);
			productsImagesRepository.save(productImages);
		}
		
		for(ProductsSizesDto productsSizesDto : productRequestDto.getProductsSizes()) {
			ProductsSizes productsSizes = new ProductsSizes();
			productsSizes.setSize(productsSizesDto.getSize());
			productsSizes.setSizesAvailable(productsSizesDto.getSizesAvailable());
			productsSizes.setProducts(products);
			productSizesRepository.save(productsSizes);
		}
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully save product");
        successResponseDto.setStatus("success");
        return ResponseEntity.ok(successResponseDto);
	}

	@Override
	public ResponseEntity<?> getAllProduct(int pageNo, int pageSize) {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) {
			throw new GlobalException("User must be logged in!!");
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Products> categories = productsRepository.findAll(pageable);

		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully get product...");
        successResponseDto.setStatus("success");
        successResponseDto.setData(categories.getContent());
        return ResponseEntity.ok(successResponseDto);
	}

	@Override
	public ResponseEntity<?> addProductBanner(ProductBannersRequestDto productBannersRequestDto) {
		
		Admin user = AppUtility.getCurrentUser();
		if(user == null) 
			throw new GlobalException("User must be logged in!!");
		
		ProductBanners products = new ProductBanners();
		String imageUrl = amazonClient.uploadFile(productBannersRequestDto.getImage(), folder);
		products.setImageUrl(imageUrl);
		products.setNavigateUrl(productBannersRequestDto.getNavigateUrl());
		productBannersRepository.save(products);
		
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully save product banner");
        successResponseDto.setStatus("success");
        return ResponseEntity.ok(successResponseDto);
	}

	@Override
	public ResponseEntity<?> getProductBanner() {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) {
			throw new GlobalException("User must be logged in!!");
		}
		
		List<ProductBanners> banners = productBannersRepository.findAll();
		
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully get product banners...");
        successResponseDto.setStatus("success");
        successResponseDto.setData(banners);
        return ResponseEntity.ok(successResponseDto);
	}

	@Override
	public ResponseEntity<?> updateProductBanner(ProductBannersRequestDto productBannersRequestDto) {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) 
			throw new GlobalException("User must be logged in!!");
		
		ProductBanners products = productBannersRepository.findById(productBannersRequestDto.getId()).orElse(null);
		if(products == null) {
			throw new GlobalException("Product not found!!");
		}
		if(productBannersRequestDto.getImage() != null) {
			String imageUrl = amazonClient.uploadFile(productBannersRequestDto.getImage(), folder);
			products.setImageUrl(imageUrl);
		}
		products.setNavigateUrl(productBannersRequestDto.getNavigateUrl());
		productBannersRepository.save(products);
		
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully update product banner");
        successResponseDto.setStatus("success");
        return ResponseEntity.ok(successResponseDto);
	}
	
	@Override
	public ResponseEntity<?> getProductBannerById(long id) {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) {
			throw new GlobalException("User must be logged in!!");
		}
		
		ProductBanners banners = productBannersRepository.findById(id).orElse(null);
		if(banners == null) {
			throw new GlobalException("Product not found!!");
		}
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully get product Banner...");
        successResponseDto.setStatus("success");
        successResponseDto.setData(banners);
        return ResponseEntity.ok(successResponseDto);
	}

	

}
