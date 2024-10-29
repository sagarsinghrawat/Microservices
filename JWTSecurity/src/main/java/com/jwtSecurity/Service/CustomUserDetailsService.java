package com.jwtSecurity.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jwtSecurity.Repo.UserRepo;
import com.jwtSecurity.entity.CustomUserDetails;
import com.jwtSecurity.entity.User;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userCredential = userRepo.findByUserName(username);
		return userCredential.map((user)-> new CustomUserDetails(user)).orElseThrow(() -> new UsernameNotFoundException("User Name not found"));
	}

}
