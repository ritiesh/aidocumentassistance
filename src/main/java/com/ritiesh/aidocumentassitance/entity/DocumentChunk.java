package com.ritiesh.aidocumentassitance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class DocumentChunk{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long documentId;
	
	@Column(length = 5000)
	private String chunkText;

	public DocumentChunk() {
		
	}
	
	
	public DocumentChunk(Long id, Long documentId, String chunkText) {
		super();
		this.id = id;
		this.documentId = documentId;
		this.chunkText = chunkText;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getChunkText() {
		return chunkText;
	}

	public void setChunkText(String chunkText) {
		this.chunkText = chunkText;
	}
}