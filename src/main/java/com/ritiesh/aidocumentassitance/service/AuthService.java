package com.ritiesh.aidocumentassitance.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ritiesh.aidocumentassitance.config.JwtUtil;
import com.ritiesh.aidocumentassitance.dto.LoginRequest;
import com.ritiesh.aidocumentassitance.dto.RegisterRequest;
import com.ritiesh.aidocumentassitance.entity.User;
import com.ritiesh.aidocumentassitance.repository.UserRepository;



@Service
public class AuthService{
	
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}
	
	public String register(RegisterRequest request) {
		
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("USER");
				
		
		userRepo.save(user);
		return "user registered successfully";
	}
	
	public String login(LoginRequest request) {
		
		User user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("user not found"));
		
		if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return jwtUtil.generateToken(user.getEmail());
		}
		
		throw new RuntimeException("credentials not found");
	}
	
}