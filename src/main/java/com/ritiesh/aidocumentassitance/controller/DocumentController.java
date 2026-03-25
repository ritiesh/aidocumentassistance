package com.ritiesh.aidocumentassitance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ritiesh.aidocumentassitance.service.DocumentService;

@RestController
@RequestMapping("/api/document")
public class DocumentController{
	
	private final DocumentService documentService;
	
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;

	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(
	        @RequestParam("file") MultipartFile file,
	        Authentication authentication) {

	    // ✅ Check file is not empty
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body("Please select a file to upload");
	    }

	    // ✅ Check file is PDF
	    String contentType = file.getContentType();
	    if (contentType == null || !contentType.equals("application/pdf")) {
	        return ResponseEntity.badRequest().body("Only PDF files are allowed");
	    }

	    // ✅ Check file size (max 10MB)
	    if (file.getSize() > 10 * 1024 * 1024) {
	        return ResponseEntity.badRequest().body("File size must be less than 10MB");
	    }

	    String email = authentication.getName();
	    return ResponseEntity.ok(documentService.documentUpload(file, email));
	}
}