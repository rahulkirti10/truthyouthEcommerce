package com.truthyouth.commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truthyouth.commerce.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);
	
	public Optional<User> findByMobileNo(String mobileNo);

}
