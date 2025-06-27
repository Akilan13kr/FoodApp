package com.example.Akilan.FoodPowdersApi.Config;

import com.example.Akilan.FoodPowdersApi.Entity.RazorpayCredentialsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RazorpayInitialization {

    private RazorpayCredentialsEntity credentials;

    public RazorpayInitialization() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            credentials = objectMapper.readValue(
                    new ClassPathResource("razorpay-credentials.json").getInputStream(),
                    RazorpayCredentialsEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getKeyId() {
        return credentials.getKey_id();
    }

    public String getKeySecret() {
        return credentials.getKey_secret();
    }
}
