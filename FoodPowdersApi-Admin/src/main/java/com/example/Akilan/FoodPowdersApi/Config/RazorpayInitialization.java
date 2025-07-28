package com.example.Akilan.FoodPowdersApi.Config;

import com.example.Akilan.FoodPowdersApi.Entity.RazorpayCredentialsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RazorpayInitialization {

    private RazorpayCredentialsEntity credentials;

    public RazorpayInitialization() {
        try {
            // 1. Read JSON from environment variable
            String razorpayJson = System.getenv("RAZORPAY_KEY_JSON");

            if (razorpayJson == null || razorpayJson.isEmpty()) {
                throw new IllegalStateException("RAZORPAY_KEY_JSON environment variable is missing or empty.");
            }

            // 2. Parse JSON string into credentials object
            ObjectMapper objectMapper = new ObjectMapper();
            credentials = objectMapper.readValue(razorpayJson, RazorpayCredentialsEntity.class);
        } catch (Exception e) {
            System.err.println("❌ Razorpay initialization failed.");
            e.printStackTrace();
        }
    }

    public String getKeyId() {
        return credentials != null ? credentials.getKey_id() : null;
    }

    public String getKeySecret() {
        return credentials != null ? credentials.getKey_secret() : null;
    }
}
