package com.truthyouth.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truthyouth.commerce.entities.UserRole;
import com.truthyouth.commerce.enums.RoleEnums;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	UserRole findByType(String user);

}
