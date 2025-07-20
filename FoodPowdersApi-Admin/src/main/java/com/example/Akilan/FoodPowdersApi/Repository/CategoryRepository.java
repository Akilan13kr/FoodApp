package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.CategoryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CategoryRepository {


    CategoryEntity addCategory(CategoryEntity entity) throws ExecutionException, InterruptedException;

    String uploadImage(MultipartFile image) throws IOException;


    public CategoryEntity  findByCategoryName(String categoryName);

    CategoryEntity save(CategoryEntity categoryEntity);

    CategoryEntity findByCategoryId(String id);

    List<CategoryEntity> findAllCategory();

    void deleteCategoryById(String id);

    boolean deleteImage(String imageUrl);

}
