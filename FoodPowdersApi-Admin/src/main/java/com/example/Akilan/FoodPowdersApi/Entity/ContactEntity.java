package com.example.Akilan.FoodPowdersApi.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactEntity {
    private String name;
    private String email;
    private String message;
}
