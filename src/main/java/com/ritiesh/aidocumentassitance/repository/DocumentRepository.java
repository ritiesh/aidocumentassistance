package com.ritiesh.aidocumentassitance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritiesh.aidocumentassitance.entity.Document;

public interface DocumentRepository extends JpaRepository<Document,Long>{
	
	List<Document> findByUserId(Long userId);
}