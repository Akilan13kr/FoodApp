package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.CartEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
public class CartRepositoryImplementationByFirebase implements CartRepository {

    private static final String COLLECTION_NAME = "Carts";

    private final Firestore dbFirestore;
    public CartRepositoryImplementationByFirebase(){
         dbFirestore = FirestoreClient.getFirestore();
    }

    @Override
    public Optional<CartEntity> findByUserId(String userId) {
        CollectionReference carts = dbFirestore.collection(COLLECTION_NAME);
        Query query = carts.whereEqualTo("userId", userId).limit(1);

        try {
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            if (!querySnapshot.get().isEmpty()) {
                DocumentSnapshot doc = querySnapshot.get().getDocuments().get(0);
                CartEntity cart = doc.toObject(CartEntity.class);
                if (cart != null) {
                    cart.setId(doc.getId()); // Set Firestore doc ID as cart.id
                }
                return Optional.ofNullable(cart);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteByUserId(String userId) {
        CollectionReference carts = dbFirestore.collection(COLLECTION_NAME);
        Query query = carts.whereEqualTo("userId", userId).limit(1);

        try {
            ApiFuture<QuerySnapshot> future = query.get();
            QuerySnapshot snapshot = future.get();
            if (!snapshot.isEmpty()) {
                String docId = snapshot.getDocuments().get(0).getId();
                carts.document(docId).delete();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CartEntity save(CartEntity cart) {
        CollectionReference carts = dbFirestore.collection(COLLECTION_NAME);

        // If id (document ID) already present → update
        if (cart.getId() != null && !cart.getId().isBlank()) {
            carts.document(cart.getId()).set(cart); // update by existing doc ID
        } else {
            // Add new document without ID
            DocumentReference docRef = carts.document(); // generates new doc ID
            cart.setId(docRef.getId());                 // store it in cart object
            docRef.set(cart);                           // save with auto ID
        }
        return cart;
    }
}
