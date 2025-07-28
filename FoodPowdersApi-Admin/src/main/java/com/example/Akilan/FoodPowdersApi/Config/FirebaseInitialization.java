package com.example.Akilan.FoodPowdersApi.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialization() {
        try {
            // 1. Read JSON from environment variable
            String jsonKey = System.getenv("GOOGLE_KEY_JSON");

            if (jsonKey == null || jsonKey.isEmpty()) {
                throw new IllegalStateException("GOOGLE_KEY_JSON environment variable is missing or empty.");
            }

            // 2. Create a temporary file and write the JSON content into it
            Path tempFile = Files.createTempFile("firebase", ".json");
            Files.write(tempFile, jsonKey.getBytes());

            // 3. Load credentials from the temp file
            FileInputStream serviceAccount = new FileInputStream(tempFile.toFile());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("foodpowderwebapp.firebasestorage.app")
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase initialized successfully.");
        } catch (Exception e) {
            System.err.println("❌ Firebase initialization failed.");
            e.printStackTrace();
        }
    }
}
