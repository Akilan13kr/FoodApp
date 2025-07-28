package com.example.Akilan.FoodPowdersApi.Controller;


import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderResponse;
import com.example.Akilan.FoodPowdersApi.Service.FoodPowderServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
//@CrossOrigin("*")
public class FoodPowderController {

    @Autowired
    private final FoodPowderServiceImplementation foodPowderServiceImplementation;

    @PostMapping("/foodpowders")
    public FoodPowderResponse addFoodPowder( @RequestPart("food") String foodPowderString, @RequestPart("file") MultipartFile image) throws ExecutionException, InterruptedException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FoodPowderRequest foodPowderRequest = null;
        foodPowderRequest = objectMapper.readValue(foodPowderString, FoodPowderRequest.class);
        FoodPowderResponse foodPowderResponse = foodPowderServiceImplementation.addFoodPowder(foodPowderRequest, image);
        return foodPowderResponse;
    }

    @GetMapping("/foodpowders")
    public List<FoodPowderResponse> readFoodPowders() throws ExecutionException, InterruptedException {
        return foodPowderServiceImplementation.readFoodPowders();
    }

    @GetMapping("/foodpowders/{id}")
    public FoodPowderResponse readFoodPowder(@PathVariable String id) throws ExecutionException, InterruptedException {
        return foodPowderServiceImplementation.readFoodPowder(id);
    }

    @DeleteMapping("/foodpowders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodPowder(@PathVariable String id) throws ExecutionException, InterruptedException {
        foodPowderServiceImplementation.deleteFood(id);
    }
}
