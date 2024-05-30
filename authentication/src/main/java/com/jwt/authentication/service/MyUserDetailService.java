package com.jwt.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.authentication.entity.MyUser;
import com.jwt.authentication.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService{

     @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MyUser> userDetail = repository.findByEmail(username);

        // Converting userDetail to UserDetails
        return userDetail.map(PrincipalMyUser::new)
                .orElseThrow(() ->new UsernameNotFoundException("User not found " + username));
    }

    public ResponseEntity<String> addUser(MyUser myUser) {
    	Optional<MyUser> user = repository.findByEmail(myUser.getEmail());
    	if(user.isPresent()) {
    		return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
    	}
        myUser.setPassword(encoder.encode(myUser.getPassword()));
        repository.save(myUser);
        return ResponseEntity.ok("User Added Successfully");
    }
    
}
