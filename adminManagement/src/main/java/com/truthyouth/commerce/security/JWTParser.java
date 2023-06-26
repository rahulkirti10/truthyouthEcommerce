package com.truthyouth.commerce.security;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import com.truthyouth.commerce.entities.Admin;
import com.truthyouth.commerce.entities.User;
import com.truthyouth.commerce.repository.AdminRepository;
import com.truthyouth.commerce.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Component
public class JWTParser {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Value("${jwt.app.secret}")
	private String tokenSecret;
	
	/*@Autowired
	private APITokenDAO apiTokenDAO;*/
	
	@Autowired
	private HttpServletResponse response;

	private Admin sub;

	private String token;
	
	//for bean initialization
	private JWTParser() {

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
		parseJWT(token);
	}

	public Admin getSub() {

		return sub;
	}

	private void parseJWT(String jwt) throws AuthenticationServiceException {

		//This line will throw an exception if it is not a signed JWS (as expected)
//		HttpServletResponse response = null ;
		try {
			//APIToken token = apiTokenDAO.getByToken(jwt);
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(tokenSecret))
					.parseClaimsJws(jwt).getBody();
			
			System.out.println("claims " + claims.get("userId"));
			Long id = Long.parseLong((String) claims.get("userId"));
			sub = adminRepository.findById(id).orElse(null);
			if(sub == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));
		}

	}

}
