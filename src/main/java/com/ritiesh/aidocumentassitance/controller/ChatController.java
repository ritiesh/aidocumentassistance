package com.ritiesh.aidocumentassitance.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritiesh.aidocumentassitance.dto.ChatRequest;
import com.ritiesh.aidocumentassitance.dto.ChatResponse;
import com.ritiesh.aidocumentassitance.entity.ChatHistory;
import com.ritiesh.aidocumentassitance.entity.User;
import com.ritiesh.aidocumentassitance.repository.ChatRepository;
import com.ritiesh.aidocumentassitance.repository.UserRepository;
import com.ritiesh.aidocumentassitance.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController{
	
	private final ChatService chatService;
	private final UserRepository userRepo;
	
	private final ChatRepository chatRepo;
	
	public ChatController( ChatService chatService, UserRepository userRepo, ChatRepository chatRepo) {
		this.chatService = chatService;
		this.userRepo = userRepo;
		
		this.chatRepo = chatRepo;
	}
	
	@PostMapping("/ask")
	public ChatResponse askQuestion(@RequestBody ChatRequest request, Authentication authentication) {
		
		String email = authentication.getName();
		String answer = chatService.askQuestion(request.getQuestion(),email);
		
		return new ChatResponse(answer);
	}
	
	@GetMapping("/history")
	public List<ChatHistory> getChatHistory(Authentication authentication){
		
		String email = authentication.getName();
		
		User user = userRepo.findByEmail(email).orElseThrow();
		
		return chatRepo.findByUserId(user.getId());
				
		
	}
}