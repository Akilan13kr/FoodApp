package com.example.Akilan.FoodPowdersApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartEntity {
    private String id;
    private String userId;
    private Map<String, Integer> items = new HashMap<>();

    public CartEntity(String userId, Map<String, Integer> items){
        this.userId = userId;
        this.items = items;
    }
}
