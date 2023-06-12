package com.truthyouth.commerce.config;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger {

	@Value("${jwt.token}")
	private String jwtToken;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo())
				.ignoredParameterTypes(Principal.class)
				.securityContexts(getSecurityContext())
				.securitySchemes(Arrays.asList(getApiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.truthyouth.commerce.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiKey getApiKey() {
		return new ApiKey("JWT", jwtToken, "header");
	}
	
	private List<SecurityContext> getSecurityContext() {
		return Arrays.asList(SecurityContext.builder().securityReferences(getSecurityReference()).build());
	}
	
	private List<SecurityReference> getSecurityReference() {
		AuthorizationScope scope = new AuthorizationScope("global", "accesseverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}
	
	private ApiInfo getInfo() {
		return new ApiInfo("Truth Youth E-commerce Backend", "Truth Youth E-commerce User Backend", "1.0", null, null, null, null, Collections.emptyList());
	}
}
