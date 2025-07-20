package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.ContactEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class ContactRepositoryImplementationByFireBase implements ContactRepository{

    private static final String COLLECTION_NAME = "Contact";

    private final Firestore dbFirestore;

    public ContactRepositoryImplementationByFireBase() {
        dbFirestore = FirestoreClient.getFirestore();
    }

    @Override
    public void addDetail(ContactEntity contact) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document();


        ApiFuture<WriteResult> writeResult = docRef.set(contact);

        // Wait for the operation to complete
        writeResult.get();

    }
}
