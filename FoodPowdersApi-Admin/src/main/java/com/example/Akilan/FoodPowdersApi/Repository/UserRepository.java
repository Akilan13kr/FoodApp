package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.UserEntity;

import java.util.concurrent.ExecutionException;

public interface UserRepository {
    UserEntity findByEmail(String email);
    UserEntity addUser(UserEntity userEntity) throws ExecutionException, InterruptedException;
}
