package com.example.Akilan.FoodPowdersApi.Entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodPowderEntity {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String imageUrl;

}
