package com.example.Akilan.FoodPowdersApi.Controller;


import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderResponse;
import com.example.Akilan.FoodPowdersApi.Service.FirebaseServiceImplementation;
import com.example.Akilan.FoodPowdersApi.Service.FoodPowderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/foodpowders")
@CrossOrigin("*")
public class FoodPowderController {

    @Autowired
    private FoodPowderService firebaseServiceImplementation;

    @PostMapping
    public FoodPowderResponse addFoodPowder( @RequestPart("food") String foodPowderString, @RequestPart("file") MultipartFile image) throws ExecutionException, InterruptedException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FoodPowderRequest foodPowderRequest = null;
        foodPowderRequest = objectMapper.readValue(foodPowderString, FoodPowderRequest.class);
        FoodPowderResponse foodPowderResponse = firebaseServiceImplementation.addFoodPowder(foodPowderRequest, image);
        return foodPowderResponse;
    }

    @GetMapping
    public List<FoodPowderResponse> readFoodPowders() throws ExecutionException, InterruptedException {
        return firebaseServiceImplementation.readFoodPowders();
    }

    @GetMapping("/{id}")
    public FoodPowderResponse readFoodPowder(@PathVariable String id) throws ExecutionException, InterruptedException {
        return firebaseServiceImplementation.readFoodPowder(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodPowder(@PathVariable String id) throws ExecutionException, InterruptedException {
        firebaseServiceImplementation.deleteFood(id);
    }
}
