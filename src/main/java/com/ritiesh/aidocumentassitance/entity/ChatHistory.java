package com.ritiesh.aidocumentassitance.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChatHistory{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	
	@Column(length = 5000)
	private String question;
	@Column(length = 10000)
	private String answer;
	private LocalDateTime createdAt;
	
	public ChatHistory() {
		
	}
	public ChatHistory(Long id, Long userId, String question, String answer, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.question = question;
		this.answer = answer;
		this.createdAt = createdAt;
	}
	public Long getId() {
		return id;
	}
	public Long getUserId() {
		return userId;
	}
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}