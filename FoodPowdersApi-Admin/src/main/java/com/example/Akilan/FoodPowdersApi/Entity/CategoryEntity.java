package com.example.Akilan.FoodPowdersApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {
    private String id;
    private String imageUrl;
    private String categoryName;
    private int countOfFoodUnderCategory;
    private Map<String, String> listOfFoodPowders;
}
