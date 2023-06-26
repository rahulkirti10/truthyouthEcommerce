package com.truthyouth.commerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truthyouth.commerce.dto.request.UserRequestDto;
import com.truthyouth.commerce.dto.request.UserSignupRequestDto;
import com.truthyouth.commerce.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> userSignup(@RequestBody UserSignupRequestDto userRequestDto){
		return userService.userSignup(userRequestDto);
	}
	
	@PostMapping("/verifyOtp")
	public ResponseEntity<?> verifyOtp(@RequestBody UserSignupRequestDto userRequestDto, HttpServletResponse response, HttpServletRequest request){
		return userService.verifyOtp(userRequestDto, response, request);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody UserRequestDto userRequestDto){
		return userService.userLogin(userRequestDto);
	}
	
	@GetMapping("/getProfile")
	public ResponseEntity<?> getProfile(){
		return userService.getProfile();
	}
	
}
