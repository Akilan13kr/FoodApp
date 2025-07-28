package com.example.Akilan.FoodPowdersApi.Repository;

import com.example.Akilan.FoodPowdersApi.Entity.FoodPowderEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
public class FoodPowderRepositoryImplementationByFirebase implements FoodPowderRepository{

    private static final String COLLECTION_NAME = "FoodPowderList";

    private final Firestore dbFirestore;
    private final Bucket bucket;

    public FoodPowderRepositoryImplementationByFirebase() {
        dbFirestore = FirestoreClient.getFirestore();
        bucket = StorageClient.getInstance().bucket();
    }

    public FoodPowderEntity addFoodPowder(FoodPowderEntity newfoodPowderEntity) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document();

        newfoodPowderEntity.setId(docRef.getId());

        //save to firestore
        ApiFuture<WriteResult> writeResult = docRef.set(newfoodPowderEntity);

        // Wait for the operation to complete
        writeResult.get();
        return newfoodPowderEntity;
    }

    public String uploadImage(MultipartFile imagefile) throws IOException {

        InputStream inputStream = imagefile.getInputStream();
        String fileExtension = imagefile.getOriginalFilename().substring(imagefile.getOriginalFilename().lastIndexOf(".") + 1);
        String key = UUID.randomUUID() + "." + fileExtension;


        Blob blob = bucket.create("ImagesOfFood/"+key, inputStream, imagefile.getContentType());

        if (blob != null) {
            // Optionally make the file public (you can remove this if not needed)
            blob.createAcl(com.google.cloud.storage.Acl.of(com.google.cloud.storage.Acl.User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER));


            return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), "ImagesOfFood/"+key);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload failed - no response from storage");
        }
    }

    public List<QueryDocumentSnapshot> readFoodPowders() throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> query = dbFirestore.collection(COLLECTION_NAME).get();
        QuerySnapshot querySnapshot = query.get();
        return querySnapshot.getDocuments();
    }

    public DocumentSnapshot readFoodPowder(String id) throws InterruptedException, ExecutionException {
        ApiFuture<DocumentSnapshot> query =  dbFirestore.collection(COLLECTION_NAME).document(id).get();
        DocumentSnapshot foodPowderDocument = query.get();

        if (!foodPowderDocument.exists()) {
            throw new RuntimeException("Food powder not founded for the id"+ id);
        }
        return foodPowderDocument;
    }


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

    public void deleteFood(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> query = docRef.get();//can use id or response.getId()
        DocumentSnapshot deletedocument = query.get();

        ApiFuture<WriteResult> deleteFuture = docRef.delete();
        deleteFuture.get();

        if (!deletedocument.exists()) {
            throw new RuntimeException("Food powder with ID " + id + " not found");
        }
    }

}
