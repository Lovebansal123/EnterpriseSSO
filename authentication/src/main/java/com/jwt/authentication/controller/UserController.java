package com.jwt.authentication.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.authentication.entity.AuthRequest;
import com.jwt.authentication.entity.MyUser;
import com.jwt.authentication.entity.UserProfileResponse;
import com.jwt.authentication.repository.UserRepository;
import com.jwt.authentication.service.ForgotPasswordService;
import com.jwt.authentication.service.JwtService;
import com.jwt.authentication.service.MyUserDetailService;
import com.jwt.authentication.service.PrincipalMyUser;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private MyUserDetailService service;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
	private UserRepository userrepo;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private ForgotPasswordService fpservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure.";
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@Valid @RequestBody MyUser myUser) {
        return service.addUser(myUser);
    }
    
    @PostMapping("/logout")
    public String userLogout() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	PrincipalMyUser user =(PrincipalMyUser)authentication.getPrincipal();
    	user.setIsLoggedOut(true);
    	return "User logged out successfuly";
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserProfileResponse> userProfile() {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	 PrincipalMyUser user =(PrincipalMyUser)authentication.getPrincipal();
    	 UserProfileResponse response = new UserProfileResponse(user.getName(),user.getUsername(),user.getAuthorities());
    	 return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token =  jwtService.generateToken(authRequest.getEmail());
            String email = jwtService.extractUsername(token);
    		Optional<MyUser> user = userrepo.findByEmail(email);
    		if(!user.isEmpty()) {
    			user.get().setLoggedOut(false);
    			userrepo.save(user.get());
    		}
    		else {
    			return new ResponseEntity<String>("Enter valid credentials", HttpStatus.NOT_FOUND);
    		}
    		return ResponseEntity.ok(token);
        } else {
            return new ResponseEntity<String>("Invalid credentials", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        Optional<MyUser> user = userrepo.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        MyUser myUser = user.get();
        String token = jwtService.generateToken(myUser.getEmail());
        user.get().setLoggedOut(false);
        userrepo.save(user.get());
        fpservice.sendPasswordResetEmail(email, token);

        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody  AuthRequest authRequest) {
        PrincipalMyUser user = fpservice.validatePasswordResetToken(token, authRequest.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        Optional<MyUser> myUser = userrepo.findByEmail(authRequest.getEmail());
        if(myUser.isEmpty()) {
        	return ResponseEntity.badRequest().body("user not found");
        }
        myUser.get().setPassword(encoder.encode(authRequest.getPassword()));
        userrepo.save(myUser.get());
        return ResponseEntity.ok("Password reset successfully");
    }
}
