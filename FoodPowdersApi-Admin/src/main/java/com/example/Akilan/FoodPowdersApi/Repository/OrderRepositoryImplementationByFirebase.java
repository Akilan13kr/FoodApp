package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.OrderEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class OrderRepositoryImplementationByFirebase implements OrderRepository {

    private static final String COLLECTION_NAME = "Orders";

    private final Firestore dbFirestore;

    public OrderRepositoryImplementationByFirebase() {
        this.dbFirestore = FirestoreClient.getFirestore();
    }

    @Override
    public List<OrderEntity> findByUserId(String userId) {
        List<OrderEntity> orderList = new ArrayList<>();
        try {
            CollectionReference collection = dbFirestore.collection(COLLECTION_NAME);
            ApiFuture<QuerySnapshot> query = collection.whereEqualTo("userId", userId).get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                OrderEntity order = doc.toObject(OrderEntity.class);
                order.setId(doc.getId()); // Set Firestore document ID
                orderList.add(order);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public Optional<OrderEntity> findByRazorpayOrderId(String razorpayOrderId) {
        try {
            CollectionReference collection = dbFirestore.collection(COLLECTION_NAME);
            ApiFuture<QuerySnapshot> query = collection.whereEqualTo("razorpayOrderId", razorpayOrderId).get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            if (!documents.isEmpty()) {
                DocumentSnapshot doc = documents.get(0);
                OrderEntity order = doc.toObject(OrderEntity.class);
                order.setId(doc.getId());
                return Optional.of(order);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        try {
            CollectionReference collection = dbFirestore.collection(COLLECTION_NAME);
            DocumentReference docRef;
            if (orderEntity.getId() != null && !orderEntity.getId().isEmpty()) {
                docRef = collection.document(orderEntity.getId());
            } else {
                docRef = collection.document(); // Auto-generate ID
                orderEntity.setId(docRef.getId());
            }
            ApiFuture<WriteResult> result = docRef.set(orderEntity);
            result.get(); // Wait for the write to complete
            return orderEntity;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(String orderId) {
        try {
            DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(orderId);
            ApiFuture<WriteResult> writeResult = docRef.delete();
            writeResult.get(); // Wait for the deletion to complete
            System.out.println("Order with ID " + orderId + " deleted at: " + writeResult.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderEntity> findAll() {
        List<OrderEntity> orderList = new ArrayList<>();
        try {
            CollectionReference collection = dbFirestore.collection(COLLECTION_NAME);
            ApiFuture<QuerySnapshot> query = collection.get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                OrderEntity order = doc.toObject(OrderEntity.class);
                order.setId(doc.getId()); // Include the document ID
                orderList.add(order);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public Optional<OrderEntity> findById(String orderId) {
        try {
            DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(orderId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                OrderEntity order = document.toObject(OrderEntity.class);
                order.setId(document.getId());
                return Optional.of(order);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }



}
