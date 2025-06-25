package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.UserEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
@Component
public class UserRepositoryImplementationByFirebase implements UserRepository{

    public Firestore dbFirestore;

    private static final String COLLECTION_NAME = "Users";

    public UserRepositoryImplementationByFirebase(){
        dbFirestore = FirestoreClient.getFirestore();
    }


    public UserEntity findByEmail(String email) {
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

            UserEntity newuser = new UserEntity();
            newuser.setId(document.getString("id"));
            newuser.setEmail(document.getString("email"));
            newuser.setName(document.getString("name"));
            newuser.setPassword(document.getString("password"));
            return newuser;

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new UsernameNotFoundException("Error fetching user data from Firebase", e);
        }
    }

    public UserEntity addUser(UserEntity newUser) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document();

        newUser.setId(docRef.getId());
        //save to firestore
        ApiFuture<WriteResult> writeResult = docRef.set(newUser);

        // Wait for the operation to complete
        writeResult.get();
        return newUser;
    }

}
