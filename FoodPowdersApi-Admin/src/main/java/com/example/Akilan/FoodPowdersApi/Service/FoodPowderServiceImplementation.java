package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.FoodPowderEntity;
import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderResponse;
import com.example.Akilan.FoodPowdersApi.Repository.FoodPowderRepository;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FoodPowderServiceImplementation implements FoodPowderService{

    private final CategoryService categoryService;
    private final FoodPowderRepository foodPowderRepository;

    public FoodPowderServiceImplementation(@Lazy CategoryService categoryService, FoodPowderRepository foodPowderRepository) {
        this.categoryService = categoryService;
        this.foodPowderRepository = foodPowderRepository;
    }

    

    @Override
    public FoodPowderResponse addFoodPowder(FoodPowderRequest foodPowderRequest, MultipartFile imageFile) throws ExecutionException, InterruptedException, IOException {
        String imagepublicUrl = foodPowderRepository.uploadImage(imageFile);
        FoodPowderEntity newfoodPowderEntity = convertToEntity(foodPowderRequest);
        newfoodPowderEntity.setImageUrl(imagepublicUrl);
        newfoodPowderEntity = foodPowderRepository.addFoodPowder(newfoodPowderEntity);
        categoryService.updateListInCategory(newfoodPowderEntity);
        return convertToResponse(newfoodPowderEntity);
    }

    @Override
    public List<FoodPowderResponse> readFoodPowders() throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> foodPowderDocuments = foodPowderRepository.readFoodPowders();
        return foodPowderDocuments.stream().map(object -> convertToResponse(object)).collect(Collectors.toList());
    }

    

    @Override
    public FoodPowderResponse readFoodPowder(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot foodPowderDocument = foodPowderRepository.readFoodPowder(id);
        return convertToResponse(foodPowderDocument);
    }



    @Override
    public void deleteFood(String id) throws ExecutionException, InterruptedException {
        FoodPowderResponse foodPowderResponse = readFoodPowder(id);
        String imageUrl = foodPowderResponse.getImageUrl();
        String filename = "ImagesOfFood/" + imageUrl.substring(imageUrl.lastIndexOf("/")+ 1);
        if(foodPowderRepository.deleteImage(filename)){
            categoryService.removeFoodFromCategory(id , foodPowderResponse.getCategory(), foodPowderResponse.getName());
            foodPowderRepository.deleteFood(id);
        }
    }


    private FoodPowderEntity convertToEntity(FoodPowderRequest request){
        return FoodPowderEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .build();
    }

    //Method OverLoading (QueryDocumentSnapshot as parameter)
    private FoodPowderResponse convertToResponse(QueryDocumentSnapshot document) {
        return FoodPowderResponse.builder()
                .id(document.getId())
                .name(document.getString("name"))
                .description(document.getString("description"))
                .category(document.getString("category"))
                .price(document.getDouble("price"))
                .imageUrl(document.getString("imageUrl"))
                .build();
    }
    //Method OverLoading (FoodPowderEntity as parameter)
    private FoodPowderResponse convertToResponse(FoodPowderEntity entity){
        return FoodPowderResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .build();
    }
    //Method OverLoading (DocumentSnapshot as parameter
    private FoodPowderResponse convertToResponse(DocumentSnapshot foodPowderDocument) {
        return FoodPowderResponse.builder()
                .id(foodPowderDocument.getId())
                .name(foodPowderDocument.getString("name"))
                .description(foodPowderDocument.getString("description"))
                .category(foodPowderDocument.getString("category"))
                .price(foodPowderDocument.getDouble("price"))
                .imageUrl(foodPowderDocument.getString("imageUrl"))
                .build();
    }
}
