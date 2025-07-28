package com.example.Akilan.FoodPowdersApi.IO_Objects;

import com.example.Akilan.FoodPowdersApi.Entity.FoodPowderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private String categoryName;
}

