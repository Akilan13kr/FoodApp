package com.example.Akilan.FoodPowdersApi.Controller;

import com.example.Akilan.FoodPowdersApi.IO_Objects.CategoryRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.CategoryResponse;
import com.example.Akilan.FoodPowdersApi.Service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
//@CrossOrigin("*")
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString, @RequestPart("file") MultipartFile image) throws IOException, ExecutionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryRequest categoryRequest;
        categoryRequest = objectMapper.readValue(categoryString, CategoryRequest.class);
        return categoryService.addCategory(categoryRequest, image);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable("id") String id){
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<CategoryResponse> getAll(){
        return categoryService.getAllCategory();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        categoryService.deleteCategoryById(id);
    }
}
