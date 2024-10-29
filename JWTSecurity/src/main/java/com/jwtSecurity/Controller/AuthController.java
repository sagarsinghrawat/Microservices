package com.jwtSecurity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jwtSecurity.Service.JwtService;
import com.jwtSecurity.Service.UserService;
import com.jwtSecurity.entity.AuthRequest;
import com.jwtSecurity.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		
		ResponseEntity<String> responseEntity = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseEntity.getBody());
	}
	
	@PostMapping("/token")
	public ResponseEntity<String>  generateToken(@RequestBody AuthRequest authRequest) {
		Authentication authenticator = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if(authenticator.isAuthenticated()) {
			String msg = jwtService.generateToken(authRequest.getUserName());
			return ResponseEntity.status(HttpStatus.OK).body(msg);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User");
		}
		
	}
	
	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
		Jws<Claims> jaws = jwtService.validateToken(token);
		return ResponseEntity.status(HttpStatus.OK).body("Token is valid");
	}
}
