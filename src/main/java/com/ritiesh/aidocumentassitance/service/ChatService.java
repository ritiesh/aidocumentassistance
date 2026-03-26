package com.ritiesh.aidocumentassitance.service;

import java.time.LocalDateTime;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.ritiesh.aidocumentassitance.entity.Document;

import com.ritiesh.aidocumentassitance.entity.ChatHistory;
import com.ritiesh.aidocumentassitance.entity.DocumentChunk;
import com.ritiesh.aidocumentassitance.entity.User;
import com.ritiesh.aidocumentassitance.repository.ChatRepository;
import com.ritiesh.aidocumentassitance.repository.ChunkRepository;
import com.ritiesh.aidocumentassitance.repository.DocumentRepository;
import com.ritiesh.aidocumentassitance.repository.UserRepository;


@Service
public class ChatService {

    private final ChunkRepository chunkRepo;
    private final AiService aiService;
    private final UserRepository userRepo;
    private final ChatRepository chatRepo;
    private final DocumentRepository documentRepository; // ✅ add this

    public ChatService(ChunkRepository chunkRepo, AiService aiService,
                       UserRepository userRepo, ChatRepository chatRepo,
                       DocumentRepository documentRepository) {
        this.chunkRepo = chunkRepo;
        this.aiService = aiService;
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
        this.documentRepository = documentRepository; // ✅ add this
    }

    public String askQuestion(String question, String email) {

        // ✅ Get current user
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Get only THIS user's documents
        List<Document> userDocs = documentRepository.findByUserId(user.getId());

        if (userDocs.isEmpty()) {
            return "Please upload a document first before asking questions.";
        }

        // ✅ Get only THIS user's document IDs
        List<Long> docIds = userDocs.stream()
                .map(Document::getId)
                .collect(Collectors.toList());

        // ✅ Get only chunks belonging to this user's documents
        List<DocumentChunk> chunks = chunkRepo.findByDocumentIdIn(docIds);

        if (chunks.isEmpty()) {
            return "No content found in your documents. Please re-upload.";
        }

        // Build context from chunks
        String context = chunks.stream()
                .map(DocumentChunk::getChunkText)
                .limit(5)
                .collect(Collectors.joining("\n"));

        String answer = aiService.askAI(context, question);

        // Save to history
        ChatHistory his = new ChatHistory();
        his.setUserId(user.getId());
        his.setAnswer(answer);
        his.setQuestion(question);
        his.setCreatedAt(LocalDateTime.now());
        chatRepo.save(his);

        return answer;
    }
}