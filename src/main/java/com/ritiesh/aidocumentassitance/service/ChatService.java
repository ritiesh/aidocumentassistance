package com.ritiesh.aidocumentassitance.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ritiesh.aidocumentassitance.entity.ChatHistory;
import com.ritiesh.aidocumentassitance.entity.DocumentChunk;
import com.ritiesh.aidocumentassitance.entity.User;
import com.ritiesh.aidocumentassitance.repository.ChatRepository;
import com.ritiesh.aidocumentassitance.repository.ChunkRepository;
import com.ritiesh.aidocumentassitance.repository.UserRepository;

@Service
public class ChatService{
	
	private final ChunkRepository chunkRepo;
	private final AiService aiService;
	private final UserRepository userRepo;
	private final ChatRepository chatRepo;
	
	public ChatService(ChunkRepository chunkRepo, AiService aiService, UserRepository userRepo, ChatRepository chatRepo) {
		this.chunkRepo = chunkRepo;
		this.aiService = aiService;
		this.userRepo = userRepo;
		this.chatRepo = chatRepo;
	}
	
	public String askQuestion(String question, String email) {
		
		User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
		List<DocumentChunk> chunks = chunkRepo.findAll();
		
		String context = chunks.stream().map(DocumentChunk::getChunkText).limit(5)
				.collect(Collectors.joining("\n"));
		
		String answer =  aiService.askAI(context, question);
		
		ChatHistory his = new ChatHistory();
		his.setUserId(user.getId());
		his.setAnswer(answer);
		his.setQuestion(question);
		his.setCreatedAt(LocalDateTime.now());
		
		chatRepo.save(his);
		return answer;
		
	}
}