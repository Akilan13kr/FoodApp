package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.CategoryEntity;
import com.example.Akilan.FoodPowdersApi.Entity.FoodPowderEntity;
import com.example.Akilan.FoodPowdersApi.IO_Objects.CategoryRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.CategoryResponse;
import com.example.Akilan.FoodPowdersApi.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImplementation implements CategoryService{

    private CategoryRepository categoryRepository;

    private FoodPowderService foodPowderService;

    @Override
    public CategoryResponse getCategoryById(String id) {
        return convertToResponse(categoryRepository.findByCategoryId(id));
    }

    public CategoryResponse addCategory(CategoryRequest request, MultipartFile image) throws IOException, ExecutionException, InterruptedException {
        String imagePublicUrl = categoryRepository.uploadImage(image);
        CategoryEntity newCategory = convertToEntity(request);
        newCategory.setImageUrl(imagePublicUrl);
        newCategory.setListOfFoodPowders(new HashMap<>());
        newCategory.setCountOfFoodUnderCategory(0);
        return convertToResponse(categoryRepository.addCategory(newCategory));
    }

    @Override
    public void updateListInCategory(FoodPowderEntity foodPowder) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(foodPowder.getCategory());
        int count = categoryEntity.getCountOfFoodUnderCategory();
        Map<String, String> foodList = categoryEntity.getListOfFoodPowders();
        foodList.put(foodPowder.getName(), foodPowder.getId());
        categoryEntity.setListOfFoodPowders(foodList);
        categoryEntity.setCountOfFoodUnderCategory(count + 1);
        categoryRepository.save(categoryEntity);

    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<CategoryEntity> categories = categoryRepository.findAllCategory();
        return categories.stream().map(category -> convertToResponse(category)).collect(Collectors.toList());
    }

    @Override
    public void deleteCategoryById(String id) throws ExecutionException, InterruptedException {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(id);
        Collection<String> foodPowdersList = categoryEntity.getListOfFoodPowders().values();
        String imageUrl = categoryEntity.getImageUrl();
        String filename = "ImagesOfCategory/" + imageUrl.substring(imageUrl.lastIndexOf("/")+ 1);
        if(categoryRepository.deleteImage(filename)) {
            for (String foodPowderId : foodPowdersList) {
                foodPowderService.deleteFood(foodPowderId);
            }
            categoryRepository.deleteCategoryById(id);
        }
    }

    @Override
    public void removeFoodFromCategory(String id, String category, String name) throws ExecutionException, InterruptedException {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(category);
        Map<String, String> listOfCategory = categoryEntity.getListOfFoodPowders();
        if(listOfCategory.containsValue(id)){
            if(categoryEntity.getCountOfFoodUnderCategory() > 0){
                listOfCategory.remove(name);
            }
            int count = categoryEntity.getCountOfFoodUnderCategory() - 1;
            categoryEntity.setCountOfFoodUnderCategory(count);
            categoryRepository.save(categoryEntity);
        }
    }

    private CategoryResponse convertToResponse(CategoryEntity categoryEntity) {
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .categoryName(categoryEntity.getCategoryName())
                .imageUrl(categoryEntity.getImageUrl())
                .countOfFoodUnderCategory(categoryEntity.getCountOfFoodUnderCategory())
                .listOfFoodPowders(categoryEntity.getListOfFoodPowders())
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryName(request.getCategoryName())
                .build();
    }

}
