package com.ritiesh.aidocumentassitance.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
public class TestController{
	
	@GetMapping
	public String test() {
		return "jwt is working fine";
	}
}