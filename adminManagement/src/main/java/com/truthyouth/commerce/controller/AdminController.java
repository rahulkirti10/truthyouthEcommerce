package com.truthyouth.commerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truthyouth.commerce.dto.request.UserRequestDto;
import com.truthyouth.commerce.dto.request.UserSignupRequestDto;
import com.truthyouth.commerce.service.AdminService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/login")
	public ResponseEntity<?> adminLogin(@RequestBody UserRequestDto requestDto, HttpServletRequest request, HttpServletResponse response){
		return adminService.adminLogin(requestDto, response, request);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> adminSignup(@RequestBody UserSignupRequestDto requestDto){
		return adminService.adminSignup(requestDto);
	}
	
	@GetMapping("/getProfile")
	public ResponseEntity<?> getProfile(){
		return adminService.getProfile();
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> adminLogout( HttpServletResponse response, HttpServletRequest request){
		return adminService.adminLogout(response, request);
	}
}
