package com.truthyouth.commerce.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.truthyouth.commerce.entities.Admin;
import com.truthyouth.commerce.entities.User;


public class CustomTokenAuthenticationFilter extends OncePerRequestFilter{

	@Value("${jwt.token}")
	private String authToken;

	@Autowired
	private JWTAuthenticationToken jwtToken;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extractJwtTokenFromCookie(request);
		if(token == null) {
			token = request.getHeader(authToken);
		}
		try {
			authUserByToken(token);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		Admin user = (Admin) jwtToken.getPrincipal();
	
		if(user == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, jwtToken.getCredentials(), user.getAuthorities());

		if(jwtToken == null){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;        	
		} 
	
		
		/*if(!authService.isTokenValid(ipAddress,token)){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;

		}*/
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	/**
	 * authenticate the user based on token
	 * @return
	 */
	private void authUserByToken(String token) throws Exception{
		jwtToken.setParser(token);
	}
	
	private String extractJwtTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            	System.out.println(cookie.getName());
                if ("authToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}