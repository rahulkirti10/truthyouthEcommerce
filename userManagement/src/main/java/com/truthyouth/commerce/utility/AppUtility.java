package com.truthyouth.commerce.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.truthyouth.commerce.entities.User;

public class AppUtility {

	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(null != auth) {
			User user = (User) auth.getPrincipal();
			return user;
		}
		return null;
	}

}
