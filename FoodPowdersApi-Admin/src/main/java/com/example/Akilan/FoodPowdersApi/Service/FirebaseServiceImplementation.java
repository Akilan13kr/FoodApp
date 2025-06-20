package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Entity.FoodPowderEntity;
import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.FoodPowderResponse;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.storage.v2.DeleteObjectRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FirebaseServiceImplementation implements FoodPowderService{

    private static final String COLLECTION_NAME = "FoodPowderList";
    Firestore dbFirestore = null;
    Bucket bucket = null;

    private void initiateFireStoreService(){
         dbFirestore = FirestoreClient.getFirestore();
    }

    private void initiateStorageClient(){
        bucket = StorageClient.getInstance().bucket();
    }


    @Override
    public String uploadImage(MultipartFile imagefile) throws IOException {
        initiateStorageClient();
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

    @Override
    public FoodPowderResponse addFoodPowder(FoodPowderRequest foodPowderRequest, MultipartFile imageFile) throws ExecutionException, InterruptedException, IOException {

        FoodPowderEntity newfoodPowderEntity = convertToEntity(foodPowderRequest);
        String imagepublicUrl = uploadImage(imageFile);
        newfoodPowderEntity.setImageUrl(imagepublicUrl);
        initiateFireStoreService();


        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document();

        newfoodPowderEntity.setId(docRef.getId());
        //save to firestore
        ApiFuture<WriteResult> writeResult = docRef.set(newfoodPowderEntity);

        // Wait for the operation to complete
        writeResult.get();

        return convertToResponse(newfoodPowderEntity);



    }

    @Override
    public List<FoodPowderResponse> readFoodPowders() throws ExecutionException, InterruptedException {
        initiateFireStoreService();
        ApiFuture<QuerySnapshot> query = dbFirestore.collection(COLLECTION_NAME).get();
        QuerySnapshot querySnapshot = query.get();

        List<QueryDocumentSnapshot> foodPowderDocuments = querySnapshot.getDocuments();
        return foodPowderDocuments.stream().map(object -> convertToResponse(object)).collect(Collectors.toList());
    }

    @Override
    public FoodPowderResponse readFoodPowder(String id) throws ExecutionException, InterruptedException {
        initiateFireStoreService();

        ApiFuture<DocumentSnapshot> query =  dbFirestore.collection(COLLECTION_NAME).document(id).get();
        DocumentSnapshot foodPowderDocument = query.get();

        if (!foodPowderDocument.exists()) {
            throw new RuntimeException("Food powder not founded for the id"+id);
        }

        return convertToResponse(foodPowderDocument);

    }

    @Override
    public boolean deleteImage(String imageName) {
        initiateStorageClient();
        Blob blob = bucket.get(imageName);

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

    @Override
    public void deleteFood(String id) throws ExecutionException, InterruptedException {
        FoodPowderResponse foodPowderResponse = readFoodPowder(id);

        String imageUrl = foodPowderResponse.getImageUrl();

        String filename = "ImagesOfFood/" + imageUrl.substring(imageUrl.lastIndexOf("/")+ 1);

        if(deleteImage(filename)){
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


    private FoodPowderEntity convertToEntity(FoodPowderRequest request){
        return FoodPowderEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .build();
    }

    //Method OverLoading (QueryDocumentSnapshot as parameter)
    private FoodPowderResponse convertToResponse(QueryDocumentSnapshot document) {
        return FoodPowderResponse.builder()
                .id(document.getId())
                .name(document.getString("name"))
                .description(document.getString("description"))
                .category(document.getString("category"))
                .price(document.getDouble("price"))
                .imageUrl(document.getString("imageUrl"))
                .build();
    }
    //Method OverLoading (FoodPowderEntity as parameter)
    private FoodPowderResponse convertToResponse(FoodPowderEntity entity){
        return FoodPowderResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .build();
    }
    //Method OverLoading (DocumentSnapshot as parameter
    private FoodPowderResponse convertToResponse(DocumentSnapshot foodPowderDocument) {
        return FoodPowderResponse.builder()
                .id(foodPowderDocument.getId())
                .name(foodPowderDocument.getString("name"))
                .description(foodPowderDocument.getString("description"))
                .category(foodPowderDocument.getString("category"))
                .price(foodPowderDocument.getDouble("price"))
                .imageUrl(foodPowderDocument.getString("imageUrl"))
                .build();
    }
}
