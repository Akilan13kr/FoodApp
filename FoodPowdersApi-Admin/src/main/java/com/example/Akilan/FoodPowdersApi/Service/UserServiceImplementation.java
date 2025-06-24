package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.UserEntity;
import com.example.Akilan.FoodPowdersApi.IO_Objects.UserRegisterRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.UserRegisterResponse;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImplementation implements UserService{

    private final PasswordEncoder passwordEncoder;

    private static final String COLLECTION_NAME = "Users";

    private final Firestore dbFirestore;

    public UserServiceImplementation(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        dbFirestore = FirestoreClient.getFirestore();
    }


    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) throws ExecutionException, InterruptedException {

        UserEntity newUser = convertToUserEntity(userRegisterRequest);

        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document();

        newUser.setId(docRef.getId());
        //save to firestore
        ApiFuture<WriteResult> writeResult = docRef.set(newUser);

        // Wait for the operation to complete
        writeResult.get();

        return convertToUserResponse(newUser);
    }

    private UserEntity convertToUserEntity(UserRegisterRequest userRegisterRequest){
        return UserEntity.builder()
                .email(userRegisterRequest.getEmail())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .name(userRegisterRequest.getName())
                .build();
    }

    private UserRegisterResponse convertToUserResponse(UserEntity registeredUser){
        return UserRegisterResponse.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .build();

    }


}
