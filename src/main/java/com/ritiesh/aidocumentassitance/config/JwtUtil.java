package com.ritiesh.aidocumentassitance.config;


import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil{
	
	public final String SECRET = "mysecretkeymysecretkeymysecretkey12";
	
	private Key getSignKey() {
		byte[] keyBytes = SECRET.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(String email) {
		
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+86400000))
				.signWith(getSignKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractEmail(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}