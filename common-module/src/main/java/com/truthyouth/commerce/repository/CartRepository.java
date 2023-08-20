package com.truthyouth.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truthyouth.commerce.entities.Cart;
import com.truthyouth.commerce.entities.Products;
import com.truthyouth.commerce.entities.User;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByUserAndProducts(User user, Products products);

}
