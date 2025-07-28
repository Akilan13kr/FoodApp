package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.UserEntity;
import com.example.Akilan.FoodPowdersApi.IO_Objects.UserRegisterRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.UserRegisterResponse;
import com.example.Akilan.FoodPowdersApi.Repository.UserRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService{

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationFacade authenticationFacade;

    private final UserRepository userRepository;


    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) throws ExecutionException, InterruptedException {
        UserEntity newUser = userRepository.addUser(convertToUserEntity(userRegisterRequest));
        return convertToUserResponse(newUser);
    }

    @Override
    public String findByUserId() {
        try {
            String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
            UserEntity loggedInUser = userRepository.findByEmail(loggedInUserEmail);
            return loggedInUser.getId();
        }catch(Exception e) {
            throw new UsernameNotFoundException("User not founded", e);
        }
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
