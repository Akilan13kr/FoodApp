package com.example.Akilan.FoodPowdersApi.IO_Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodPowderRequest {
    private String name;
    private String description;
    private double price;
    private String category;
}
