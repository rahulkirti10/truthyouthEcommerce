package com.truthyouth.commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truthyouth.commerce.entities.Admin;
import com.truthyouth.commerce.entities.User;

public interface AdminRepository extends JpaRepository<Admin, Long>{

	public Optional<Admin> findByEmail(String email);
	
	public Optional<Admin> findByMobileNo(String mobileNo);
}
