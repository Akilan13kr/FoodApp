package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.ContactEntity;
import com.example.Akilan.FoodPowdersApi.Repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class ContactServiceImplementation implements ContactService{

    @Autowired
    public final ContactRepository contactRepository;

    @Override
    public void addContact(ContactEntity contact) throws ExecutionException, InterruptedException {
        contactRepository.addDetail(contact);
    }
}
