package com.ritiesh.aidocumentassitance.service;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

@Service
public class PdfExtractionService {

    public String extractText(String filePath) {
        try {
            // ✅ Use absolute path
            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("File not found at path: " + filePath);
            }

            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            if (text == null || text.trim().isEmpty()) {
                throw new RuntimeException("No text could be extracted from PDF");
            }

            return text;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to extract PDF text: " + e.getMessage());
        }
    }
}