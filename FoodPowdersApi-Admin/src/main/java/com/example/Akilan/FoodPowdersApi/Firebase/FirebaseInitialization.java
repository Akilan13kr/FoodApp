package com.example.Akilan.FoodPowdersApi.Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialization() throws IOException {
        try {
            InputStream serviceAccount = getClass().getClassLoader()
                    .getResourceAsStream("serviceAccountKey.json");

            assert serviceAccount != null;
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("foodpowderwebapp.firebasestorage.app")
                    .build();

            FirebaseApp.initializeApp(options);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



}


