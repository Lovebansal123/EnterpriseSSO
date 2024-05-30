package com.jwt.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {
	  

	    @Autowired
	    private MyUserDetailService userService;

	    @Autowired
	    private JavaMailSender mailSender;
	    
	    @Autowired
	    private JwtService jwtService;
	   

	    public void sendPasswordResetEmail(String email, String token) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(email);
	        message.setSubject("Password Reset Request");
	        message.setText("To reset your password, click the link below:\n" + 
	            "http://localhost:3000/resetPassword?token=" + token);
	        mailSender.send(message);
	    }

	    public PrincipalMyUser validatePasswordResetToken(String token, String email) {
	    	PrincipalMyUser user = (PrincipalMyUser)userService.loadUserByUsername(email);
	        if (!jwtService.validateToken(token, user)) {
	            return null;
	        }
	        return user;
	    }
}
