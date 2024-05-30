package com.jwt.authentication.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UserProfileResponse {
     private String username;
     private String email;
     private Collection<? extends GrantedAuthority> authorities;
     
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserProfileResponse [username=" + username + ", email=" + email + ", authorities=" + authorities + "]";
	}
	public UserProfileResponse(String username, String email, Collection<? extends GrantedAuthority> collection) {
		super();
		this.username = username;
		this.email = email;
		this.authorities = collection;
	}
	public UserProfileResponse() {
		super();
	}
	
     
}
