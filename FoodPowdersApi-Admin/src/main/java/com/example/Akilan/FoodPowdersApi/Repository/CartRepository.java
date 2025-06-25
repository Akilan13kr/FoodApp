package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.CartEntity;

import java.util.Optional;

public interface CartRepository {
    Optional<CartEntity> findByUserId(String userId);

    void deleteByUserId(String userId);
    CartEntity save(CartEntity cart);
}
