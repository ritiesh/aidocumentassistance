package com.ritiesh.aidocumentassitance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ritiesh.aidocumentassitance.entity.DocumentChunk;

@Repository
public interface ChunkRepository extends JpaRepository<DocumentChunk,Long>{
	
	List<DocumentChunk> findByDocumentId(Long documentId);
}