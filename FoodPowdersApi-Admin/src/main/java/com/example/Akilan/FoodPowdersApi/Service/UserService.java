package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.IO_Objects.UserRegisterRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.UserRegisterResponse;

import java.util.concurrent.ExecutionException;

public interface UserService {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) throws ExecutionException, InterruptedException;
}
