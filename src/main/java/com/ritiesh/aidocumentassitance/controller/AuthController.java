package com.ritiesh.aidocumentassitance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritiesh.aidocumentassitance.dto.LoginRequest;
import com.ritiesh.aidocumentassitance.dto.RegisterRequest;
import com.ritiesh.aidocumentassitance.service.AuthService;



@RestController
@RequestMapping("/api/auth")

public class AuthController{
	
	
	public final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		return ResponseEntity.ok(authService.login(request));
	}
	
}