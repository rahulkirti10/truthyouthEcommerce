package com.truthyouth.commerce.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.truthyouth.commerce.dto.request.UserRequestDto;
import com.truthyouth.commerce.dto.request.UserSignupRequestDto;

public interface UserService {

	ResponseEntity<?> userSignup(UserSignupRequestDto userRequestDto);

	ResponseEntity<?> userLogin(UserRequestDto userRequestDto);

	ResponseEntity<?> verifyOtp(UserSignupRequestDto userRequestDto, HttpServletResponse response);

	ResponseEntity<?> getProfile();

}
