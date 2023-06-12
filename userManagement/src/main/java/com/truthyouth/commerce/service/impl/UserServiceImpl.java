package com.truthyouth.commerce.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.truthyouth.commerce.dto.request.UserRequestDto;
import com.truthyouth.commerce.dto.request.UserSignupRequestDto;
import com.truthyouth.commerce.dto.response.ResponseDto;
import com.truthyouth.commerce.dto.response.UserResponseDto;
import com.truthyouth.commerce.entities.User;
import com.truthyouth.commerce.entities.UserRole;
import com.truthyouth.commerce.enums.RoleEnums;
import com.truthyouth.commerce.exception.GlobalException;
import com.truthyouth.commerce.repository.UserRepository;
import com.truthyouth.commerce.repository.UserRoleRepository;
import com.truthyouth.commerce.service.UserService;
import com.truthyouth.commerce.utility.AppUtility;
import com.truthyouth.commerce.utility.TokenUtility;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Value("${jwt.token}")
	private String authToken;
	
	@Value("${jwt.app.secret}")
	private String appSecret;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<?> userSignup(UserSignupRequestDto userRequestDto) {
		
		 Optional<User> existingUser = userRepository.findByMobileNo(userRequestDto.getMobileNo());
		    ResponseEntity<?> responseDto = (ResponseEntity<?>) existingUser.map(user -> {
		    	 throw new GlobalException("You are already registered with us, please login.");
		    }).orElseGet(() -> {
		        User user = new User();
		        UserRole role = userRoleRepository.findByType(RoleEnums.ROLE_USER.toString());
		        user.setMobileNo(userRequestDto.getMobileNo());
		        user.setFullName(userRequestDto.getFullName());
		        user.setRole(role);
		        user.setStatus("ACTIVE");
		        user.setLoginAllowed(true);
		        user.setOtp("111111");		        
		        userRepository.save(user);
		        ResponseDto successResponseDto = new ResponseDto();
		        successResponseDto.setMessage("Successfully send otp to your mobile no.");
		        successResponseDto.setStatus("success");
		        return ResponseEntity.ok(successResponseDto);
		    });
			return responseDto;
	}

	@Override
	public ResponseEntity<?> userLogin(UserRequestDto userRequestDto) {
//		User user = userRepository.findByEmail(userRequestDto.getEmailOrMobile());
//		ResponseDto responseDto = new ResponseDto();
//		if(user == null) {
//			user = userRepository.findByMobileNo(userRequestDto.getEmailOrMobile());
//			if(user == null) {
//				responseDto.setData("Invalid Email or Mobile no.");
//			}
//		}
		return null;
	}

	@Override
	public ResponseEntity<?> verifyOtp(UserSignupRequestDto userRequestDto, HttpServletResponse response) {
	    Optional<User> existingUser = userRepository.findByMobileNo(userRequestDto.getMobileNo());
	    ResponseEntity<?> responseDto = (ResponseEntity<?>) existingUser.map(user -> {
	    	ResponseDto successResponseDto = new ResponseDto();
	        if (user.getOtp().equals(userRequestDto.getOtp())) {
	            successResponseDto.setMessage("Otp successfully matched.");
	            successResponseDto.setStatus("success");
	            String jwtToken = TokenUtility.createJWT(user, appSecret, authToken, new ArrayList<>());
	            final ResponseCookie responseCookie = ResponseCookie.from("authToken", jwtToken)
	                    .sameSite("Strict")
	                    .httpOnly(true)
	                    .maxAge(86400)
	                    .domain(".truthyouth.in")
	                    .secure(true)
	                    .path("/")
	                    .build();
	            response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
	            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
	    		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
	    		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");

	            return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
	        } else {
	            throw new GlobalException("Otp didn't match.");
	        }
	    }).orElseGet(() -> {
	    	 throw new GlobalException("Please enter a valid mobile no.");
	    });
	    return responseDto;
	}

	@Override
	public ResponseEntity<?> getProfile() {
		User user = AppUtility.getCurrentUser();
		if (user == null) {
			throw new GlobalException("User not loggedIn.");
		}
		UserResponseDto responseDto = modelMapper.map(user, UserResponseDto.class);

		ResponseDto successResponseDto = new ResponseDto();
		successResponseDto.setMessage("Successfully get User Profile");
		successResponseDto.setStatus("success");
		successResponseDto.setData(responseDto);
		return ResponseEntity.ok(successResponseDto);
	}

}