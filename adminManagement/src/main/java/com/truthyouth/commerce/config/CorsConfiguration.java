package com.truthyouth.commerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Component
public class CorsConfiguration extends WebMvcConfigurerAdapter{
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
				.allowedOrigins("http://localhost:3000", "http://3.6.54.65:3005")
				.allowCredentials(true);
	}
}
