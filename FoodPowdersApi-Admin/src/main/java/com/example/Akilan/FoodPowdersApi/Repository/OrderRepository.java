package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<OrderEntity> findByUserId(String userId);

    Optional<OrderEntity> findByRazorpayOrderId(String razorpayOrderId);

    OrderEntity save(OrderEntity orderEntity);

    void deleteById(String orderId);

    List<OrderEntity> findAll();

    Optional<OrderEntity> findById(String orderId);
}
