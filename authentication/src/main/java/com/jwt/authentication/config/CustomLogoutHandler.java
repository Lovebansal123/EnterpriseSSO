package com.jwt.authentication.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.jwt.authentication.entity.MyUser;
import com.jwt.authentication.repository.UserRepository;
import com.jwt.authentication.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@Service
public class CustomLogoutHandler implements LogoutHandler{
	 @Autowired
	 private JwtService jwtService;
	 
	 @Autowired
	 private UserRepository userrepo;

	@Override
	@CrossOrigin
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String authHeader = request.getHeader("Authorization");
        if (authHeader == null|| !authHeader.startsWith("Bearer ")) {
            return;
        }
        String token = authHeader.substring(7);
		String email = jwtService.extractUsername(token);
		Optional<MyUser> user = userrepo.findByEmail(email);
		if(!user.isEmpty()) {
			user.get().setLoggedOut(true);
			userrepo.save(user.get());
		}	
	}

}
