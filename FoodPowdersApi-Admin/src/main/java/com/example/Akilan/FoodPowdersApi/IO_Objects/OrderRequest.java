package com.example.Akilan.FoodPowdersApi.IO_Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderItem> orderedItems;
    private String userAddress;
    private double amount;
    private String email;
    private String phoneNumber;
    private String orderStatus;
}
