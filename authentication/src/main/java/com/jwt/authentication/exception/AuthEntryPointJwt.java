package com.jwt.authentication.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		  
			  AuthenticationException authException) throws IOException, ServletException {
		try { response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
		  throw new UserNotAuthorizedException("Invalid");
	}catch(UserNotAuthorizedException e) {
		
	}
	}
	
}
