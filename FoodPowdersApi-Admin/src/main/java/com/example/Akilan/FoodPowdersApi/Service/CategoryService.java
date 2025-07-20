package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.FoodPowderEntity;
import com.example.Akilan.FoodPowdersApi.IO_Objects.CategoryRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CategoryService {
    CategoryResponse getCategoryById(String id);

    CategoryResponse addCategory(CategoryRequest request, MultipartFile image) throws IOException, ExecutionException, InterruptedException;

    void updateListInCategory(FoodPowderEntity foodpowder);

    List<CategoryResponse> getAllCategory();

    void deleteCategoryById(String id) throws ExecutionException, InterruptedException;

    void removeFoodFromCategory(String id, String category, String name) throws ExecutionException, InterruptedException;

}
