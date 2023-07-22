package com.truthyouth.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truthyouth.commerce.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Long>{

}
