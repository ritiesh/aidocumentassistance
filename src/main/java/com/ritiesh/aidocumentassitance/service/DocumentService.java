package com.ritiesh.aidocumentassitance.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ritiesh.aidocumentassitance.entity.Document;
import com.ritiesh.aidocumentassitance.entity.DocumentChunk;
import com.ritiesh.aidocumentassitance.entity.User;
import com.ritiesh.aidocumentassitance.repository.ChunkRepository;
import com.ritiesh.aidocumentassitance.repository.DocumentRepository;
import com.ritiesh.aidocumentassitance.repository.UserRepository;





@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final PdfExtractionService pdfService;
    private final ChunkService chunkService;
    private final ChunkRepository chunkRepo;
    private final UserRepository userRepo;

    public DocumentService(DocumentRepository documentRepository,
                           PdfExtractionService pdfService,
                           ChunkService chunkService,
                           ChunkRepository chunkRepo,
                           UserRepository userRepo) {
        this.documentRepository = documentRepository;
        this.pdfService = pdfService;
        this.chunkService = chunkService;
        this.chunkRepo = chunkRepo;
        this.userRepo = userRepo;
    }

    public String documentUpload(MultipartFile file, String email) {
        try {
            // ✅ Fix 1: Create uploads folder if it doesn't exist
            File uploadDir = new File("uploads");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // ✅ Fix 2: Clean filename to avoid spaces/special chars
            String fileName = file.getOriginalFilename()
                    .replaceAll("[^a-zA-Z0-9._-]", "_");
            String filePath = "uploads/" + fileName;

            // ✅ Fix 3: Use transferTo instead of Files.copy
            File dest = new File(filePath);
            file.transferTo(dest.getAbsoluteFile());

            // Extract text from PDF
            String text = pdfService.extractText(dest.getAbsolutePath());

            // Split into chunks
            List<String> chunks = chunkService.splitText(text);

            // ✅ Fix 4: Set userId from email
            User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Save document
            Document doc = new Document();
            doc.setFileName(fileName);
            doc.setFilePath(filePath);
            doc.setUploadedAt(LocalDateTime.now());
            doc.setUserId(user.getId()); // ✅ was missing before

            documentRepository.save(doc);

            // Save chunks
            for (String chunk : chunks) {
                DocumentChunk documentChunk = new DocumentChunk();
                documentChunk.setDocumentId(doc.getId());
                documentChunk.setChunkText(chunk);
                chunkRepo.save(documentChunk);
            }

            return "File Uploaded Successfully";

        } catch (Exception e) {
            // ✅ Fix 5: Print actual error so you can debug
            e.printStackTrace();
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }
}