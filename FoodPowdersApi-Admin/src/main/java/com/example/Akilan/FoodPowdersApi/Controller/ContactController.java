package com.example.Akilan.FoodPowdersApi.Controller;

import com.example.Akilan.FoodPowdersApi.Entity.ContactEntity;
import com.example.Akilan.FoodPowdersApi.Service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private final ContactService contactService;

    @PostMapping
    public void addContact(@RequestBody ContactEntity contact) throws ExecutionException, InterruptedException {
        contactService.addContact(contact);
    }
}
