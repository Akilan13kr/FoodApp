package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.ContactEntity;

import java.util.concurrent.ExecutionException;

public interface ContactService {

    void addContact(ContactEntity contact) throws ExecutionException, InterruptedException;
}
