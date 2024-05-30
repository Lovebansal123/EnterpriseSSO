package com.jwt.authentication.exception;



public class UserNotAuthorizedException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UserNotAuthorizedException(String msg){
		super(msg);
	}
}
