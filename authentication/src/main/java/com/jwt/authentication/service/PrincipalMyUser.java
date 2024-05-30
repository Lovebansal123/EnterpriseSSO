package com.jwt.authentication.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwt.authentication.entity.MyUser;

public class PrincipalMyUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
    private String password;
    private boolean isUserLoggedOut;
    private List<GrantedAuthority> authorities;

    public PrincipalMyUser(MyUser myUser) {
        email = myUser.getEmail();
        password = myUser.getPassword();
        username = myUser.getName();
        isUserLoggedOut = myUser.isLoggedOut();
        authorities = Arrays.stream(myUser.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    public boolean getIsLoggedOut() {
    	return isUserLoggedOut;
    }
    public void setIsLoggedOut(Boolean isLoggedOut) {
    	isUserLoggedOut = isLoggedOut;
    }
    public String getName() {
    	return username;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
