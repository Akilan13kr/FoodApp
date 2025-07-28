package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.UserEntity;
import com.example.Akilan.FoodPowdersApi.Repository.UserRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private static final String COLLECTION_NAME = "Users";


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        try {
            // Query Firestore collection "Users" for document where email matches
            ApiFuture<QuerySnapshot> query = dbFirestore.collection(COLLECTION_NAME)
                    .whereEqualTo("email", email)
                    .get();

            QuerySnapshot querySnapshot = query.get();

            if (querySnapshot.isEmpty()) {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
            // it take the 0 index of the document where email id is unique the logic of getting email is only one
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);


            String userEmail = document.getString("email");
            String password = document.getString("password");

            return new User(userEmail, password, Collections.emptyList());

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new UsernameNotFoundException("Error fetching user data from Firebase", e);
        }
    }
}
