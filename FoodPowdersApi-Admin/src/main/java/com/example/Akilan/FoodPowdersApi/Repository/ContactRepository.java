package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.ContactEntity;

import java.util.concurrent.ExecutionException;

public interface ContactRepository {
    void addDetail(ContactEntity contact) throws ExecutionException, InterruptedException;

}
