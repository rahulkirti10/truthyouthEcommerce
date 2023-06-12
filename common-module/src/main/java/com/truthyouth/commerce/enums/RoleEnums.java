package com.truthyouth.commerce.enums;

public enum RoleEnums {
	
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_USER("ROLE_USER");
	
	
	private String userRole;
	
	private RoleEnums(String userRole) {
		this.userRole = userRole;
	}
	
	public String getRole() {
		return this.userRole;
	}
}
