package com.truthyouth.commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truthyouth.commerce.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByLoginAuthToken(String authToken);
	
	public Optional<User> findByMobileNo(String mobileNo);

}
