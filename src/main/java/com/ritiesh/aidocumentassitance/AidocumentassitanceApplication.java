package com.ritiesh.aidocumentassitance;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AidocumentassitanceApplication {

    public static void main(String[] args) {

        // ✅ Load .env BEFORE Spring starts
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );

        // Debug — confirm API_KEY is loaded
        System.out.println("API_KEY loaded: " + (System.getProperty("API_KEY") != null ? "YES" : "NO"));

        SpringApplication.run(AidocumentassitanceApplication.class, args);
    }
}