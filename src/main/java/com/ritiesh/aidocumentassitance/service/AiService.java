package com.ritiesh.aidocumentassitance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import org.springframework.beans.factory.annotation.Value;

@Service
public class AiService{
	
	
	
	@Value("${groq.api.key}")
	private String apiKey;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	public String askAI(String context, String question) {
		
		String prompt = "Answer the question using the context below:\\n\\n" + 
				context + "\n\nQuestion: " + question;
		
		
		Map<String,Object> body = new HashMap<String, Object>();
		
		body.put("model", "openai/gpt-oss-120b");
		
		List<Map<String,String>> messages = new ArrayList<>();
		
		Map<String,String> message = new HashMap<String, String>();
		
		message.put("role", "user");
		message.put("content", prompt);
		
		messages.add(message);
		
		body.put("messages", messages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey);
		
		HttpEntity<Map<String,Object>> request = new HttpEntity<>(body,headers);
		
		ResponseEntity<Map> response = restTemplate.postForEntity(
				"https://api.groq.com/openai/v1/chat/completions",request,Map.class);
		
		
		 Map<?, ?> result = (Map<?, ?>)((List<?>)response.getBody().get("choices")).get(0);
		 
		 Map<?, ?> messageResult = (Map<?, ?>)result.get("message");
		 
		

	     return messageResult.get("content").toString();
	}
	
}