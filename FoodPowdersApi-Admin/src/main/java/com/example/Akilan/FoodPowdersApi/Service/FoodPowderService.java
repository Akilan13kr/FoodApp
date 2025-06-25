package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FoodPowderService {

    FoodPowderResponse addFoodPowder(FoodPowderRequest foodPowderRequest, MultipartFile imageFile) throws ExecutionException, InterruptedException, IOException;

    List<FoodPowderResponse> readFoodPowders() throws ExecutionException, InterruptedException;

    FoodPowderResponse readFoodPowder(String id) throws ExecutionException, InterruptedException;


    void deleteFood(String id) throws ExecutionException, InterruptedException;


}
