package com.truthyouth.commerce.service.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.truthyouth.commerce.dto.request.UserRequestDto;
import com.truthyouth.commerce.dto.request.UserSignupRequestDto;
import com.truthyouth.commerce.dto.response.ResponseDto;
import com.truthyouth.commerce.dto.response.UserResponseDto;
import com.truthyouth.commerce.entities.Admin;
import com.truthyouth.commerce.entities.User;
import com.truthyouth.commerce.entities.UserRole;
import com.truthyouth.commerce.enums.RoleEnums;
import com.truthyouth.commerce.exception.GlobalException;
import com.truthyouth.commerce.repository.AdminRepository;
import com.truthyouth.commerce.repository.UserRepository;
import com.truthyouth.commerce.repository.UserRoleRepository;
import com.truthyouth.commerce.service.AdminService;
import com.truthyouth.commerce.utility.AppUtility;
import com.truthyouth.commerce.utility.TokenUtility;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${jwt.token}")
	private String authToken;
	
	@Value("${jwt.app.secret}")
	private String appSecret;
	
	@Value("${access.control.origin}")
	private String localorigin;
	
	@Value("${access.control.origin.dev}")
	private String devorigin;

	@Override
	public ResponseEntity<?> adminSignup(UserSignupRequestDto userRequestDto) {
		
		 Optional<Admin> existingUser = adminRepository.findByEmail(userRequestDto.getEmail());
		    ResponseEntity<?> responseDto = (ResponseEntity<?>) existingUser.map(user -> {
		    	 throw new GlobalException("You are already registered with us, please login.");
		    	 
		    }).orElseGet(() -> {
		    	Admin user = new Admin();
		        UserRole role = userRoleRepository.findByType(RoleEnums.ROLE_ADMIN.toString());
		        user.setMobileNo(userRequestDto.getMobileNo());
		        user.setFullName(userRequestDto.getFullName());
		        user.setEmail(userRequestDto.getEmail());
		        user.setRole(role);
		        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
		        user.setLoginAllowed(true);
		        adminRepository.save(user);
		        ResponseDto successResponseDto = new ResponseDto();
		        successResponseDto.setMessage("Successfully save admin");
		        successResponseDto.setStatus("success");
		        return ResponseEntity.ok(successResponseDto);
		    });
			return responseDto;
	}

	@Override
	public ResponseEntity<?> adminLogin(UserRequestDto userRequestDto, HttpServletResponse response, HttpServletRequest request) {
		 Optional<Admin> existingUser = adminRepository.findByEmail(userRequestDto.getEmail());
		 ResponseEntity<?> responseDto = (ResponseEntity<?>) existingUser.map(user -> {
		        
	    		if(!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
	    			 throw new GlobalException("Invalid username and password.");
	    		}
	    		
	    		String jwtToken = TokenUtility.createJWT(user, appSecret, authToken, new ArrayList<>());
	            String origin = "localhost";
	            String sameSite = "Strict";
	            if (request.getHeader("Origin").toString().startsWith("http://3.6.54.65")) {
	                origin = ".3.6.54.65";
	                sameSite = "Strict";
	            }
	            final ResponseCookie responseCookie = ResponseCookie.from("authToken", jwtToken)
	                    .sameSite(sameSite)
	                    .httpOnly(true)
	                    .maxAge(86400)
	                    .domain(origin)
	                    .secure(false)
	                    .path("/")
	                    .build();
	            response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
	            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
	    		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
	    		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		        
		        ResponseDto successResponseDto = new ResponseDto();
		        successResponseDto.setMessage("Success");
		        successResponseDto.setStatus("success");
		        return ResponseEntity.ok(successResponseDto);
	    }).orElseGet(() -> {
	    	 throw new GlobalException("Invalid username and password.");
	    });
		return responseDto;
	}

	@Override
	public ResponseEntity<?> getProfile() {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) {
			throw new GlobalException("User must be logged in!!");
		}
		UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);
		
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully get admin profile");
        successResponseDto.setStatus("success");
        successResponseDto.setData(dto);
        return ResponseEntity.ok(successResponseDto);
	}

	@Override
	public ResponseEntity<?> adminLogout(HttpServletResponse response, HttpServletRequest request) {
		Admin user = AppUtility.getCurrentUser();
		if(user == null) {
			throw new GlobalException("User must be logged in!!");
		}
		String jwtToken = TokenUtility.createJWT(user, appSecret, authToken, new ArrayList<>());
        String origin = "localhost";
        String sameSite = "Strict";
        if (request.getHeader("Origin").toString().startsWith("http://3.6.54.65")) {
            origin = ".3.6.54.65";
            sameSite = "Strict";
        }
        final ResponseCookie responseCookie = ResponseCookie.from("authToken", "")
                .sameSite(sameSite)
                .httpOnly(true)
                .maxAge(0)
                .domain(origin)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        
		
		ResponseDto successResponseDto = new ResponseDto();
        successResponseDto.setMessage("Successfully logout");
        successResponseDto.setStatus("success");
        return ResponseEntity.ok(successResponseDto);
	}


}
