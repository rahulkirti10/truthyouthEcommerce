package com.truthyouth.commerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.truthyouth.commerce.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Long>{

	@Query(value = "select count(id) from products where name like :keywords", nativeQuery = true)
	int findByKeyword(String keywords);

	@Query(value = "select * from products where name like :keyword", nativeQuery = true)
	Page<Products> findByKeywordAllProduct(String keyword, Pageable pageable);

}
