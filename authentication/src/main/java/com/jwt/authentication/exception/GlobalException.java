package com.jwt.authentication.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUserExceptions(Exception ex) {
        return new ResponseEntity<>("Enter Valid Credentials", HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(UserNotAuthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUserAuthorizationExceptions(UserNotAuthorizedException ex) {
       
        return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }
	
}
