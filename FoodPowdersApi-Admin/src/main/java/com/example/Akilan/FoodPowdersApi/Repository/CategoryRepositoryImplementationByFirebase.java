package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.CategoryEntity;
import com.example.Akilan.FoodPowdersApi.Entity.FoodPowderEntity;
import com.example.Akilan.FoodPowdersApi.Entity.OrderEntity;
import com.example.Akilan.FoodPowdersApi.IO_Objects.CategoryRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.CategoryResponse;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
public class CategoryRepositoryImplementationByFirebase implements CategoryRepository{

    private static final String COLLECTION_NAME = "CategoryList";

    private final Firestore dbFirestore;
    private final Bucket bucket;

    public CategoryRepositoryImplementationByFirebase() {
        dbFirestore = FirestoreClient.getFirestore();
        bucket = StorageClient.getInstance().bucket();
    }

    @Override
    public CategoryEntity addCategory(CategoryEntity entity) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document();

        entity.setId(docRef.getId());

        //save to firestore
        ApiFuture<WriteResult> writeResult = docRef.set(entity);

        // Wait for the operation to complete
        writeResult.get();
        return entity;
    }

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        InputStream inputStream = image.getInputStream();
        String fileExtension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
        String key = UUID.randomUUID() + "." + fileExtension;


        Blob blob = bucket.create("ImagesOfCategory/"+key, inputStream, image.getContentType());

        if (blob != null) {
            // Optionally make the file public (you can remove this if not needed)
            blob.createAcl(com.google.cloud.storage.Acl.of(com.google.cloud.storage.Acl.User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER));


            return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), "ImagesOfCategory/"+key);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload failed - no response from storage");
        }
    }




    public CategoryEntity  findByCategoryName(String categoryName) {
        try {
            CollectionReference collection = dbFirestore.collection(COLLECTION_NAME);
            ApiFuture<QuerySnapshot> query = collection.whereEqualTo("categoryName", categoryName).get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            if (!documents.isEmpty()) {
                DocumentSnapshot doc = documents.get(0);
                return doc.toObject(CategoryEntity.class);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        CollectionReference categories = dbFirestore.collection(COLLECTION_NAME);

        // If id (document ID) already present → update
        if (category.getId() != null && !category.getId().isBlank()) {
            categories.document(category.getId()).set(category); // update by existing doc ID
        } else {
            // Add new document without ID
            DocumentReference docRef = categories.document(); // generates new doc ID
            category.setId(docRef.getId());                 // store it in cart object
            docRef.set(category);                           // save with auto ID
        }
        return category;
    }

    @Override
    public CategoryEntity findByCategoryId(String id) {
        try {
            CollectionReference collection = dbFirestore.collection(COLLECTION_NAME);
            ApiFuture<QuerySnapshot> query = collection.whereEqualTo("id", id).get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            if (!documents.isEmpty()) {
                DocumentSnapshot doc = documents.get(0);
                return doc.toObject(CategoryEntity.class);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CategoryEntity> findAllCategory() {
        List<CategoryEntity> categories = new ArrayList<>();
        try{
            CollectionReference collection = dbFirestore.collection(COLLECTION_NAME);
            ApiFuture<QuerySnapshot> query = collection.get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                CategoryEntity categoryEntity = doc.toObject(CategoryEntity.class);
                categoryEntity.setId(doc.getId()); // Include the document ID
                categories.add(categoryEntity);
            }
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public void deleteCategoryById(String id) {
        CollectionReference categoriesRef = dbFirestore.collection(COLLECTION_NAME);
        Query query = categoriesRef.whereEqualTo("id", id).limit(1);

        try {
            ApiFuture<QuerySnapshot> future = query.get();
            QuerySnapshot snapshot = future.get();
            if (!snapshot.isEmpty()) {
                String docId = snapshot.getDocuments().get(0).getId();
                categoriesRef.document(docId).delete();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteImage(String imageUrl) {
        Blob blob = bucket.get(imageUrl);

        if (blob == null || !blob.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
        boolean deleted = blob.delete();

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete file");
        }else
            return true;
    }
}

