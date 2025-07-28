package com.example.Akilan.FoodPowdersApi.Controller;

import com.example.Akilan.FoodPowdersApi.IO_Objects.UserRegisterRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.UserRegisterResponse;
import com.example.Akilan.FoodPowdersApi.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
//@CrossOrigin("*")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponse registerUser(@RequestBody UserRegisterRequest userRequest) throws ExecutionException, InterruptedException {
        return userService.registerUser(userRequest);
    }
}
