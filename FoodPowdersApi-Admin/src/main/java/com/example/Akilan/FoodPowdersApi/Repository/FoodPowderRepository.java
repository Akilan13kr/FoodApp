package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.FoodPowderEntity;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FoodPowderRepository {
    FoodPowderEntity addFoodPowder(FoodPowderEntity newfoodPowderEntity) throws ExecutionException, InterruptedException;
    String uploadImage(MultipartFile imagefile) throws IOException;
    List<QueryDocumentSnapshot> readFoodPowders() throws InterruptedException, ExecutionException;
    DocumentSnapshot readFoodPowder(String id) throws InterruptedException, ExecutionException;
    boolean deleteImage(String imageName);
    void deleteFood(String id) throws ExecutionException, InterruptedException;
}
