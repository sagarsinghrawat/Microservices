package com.jwtSecurity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtSecurity.Repo.UserRepo;
import com.jwtSecurity.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<String> saveUser(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
	}
	
}
