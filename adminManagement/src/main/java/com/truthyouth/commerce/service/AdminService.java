package com.truthyouth.commerce.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.truthyouth.commerce.dto.request.UserRequestDto;
import com.truthyouth.commerce.dto.request.UserSignupRequestDto;

public interface AdminService {

	ResponseEntity<?> adminLogin(UserRequestDto requestDto, HttpServletResponse response, HttpServletRequest request);

	ResponseEntity<?> adminSignup(UserSignupRequestDto requestDto);

	ResponseEntity<?> getProfile();

	ResponseEntity<?> adminLogout(HttpServletResponse response, HttpServletRequest request);

}
