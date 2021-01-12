package com.example.Tajming.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Tajming.java.Milage;
import com.example.Tajming.java.MilageManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;

public class DAOmilage {
    private static DAOmilage instance = null;

    private DAOmilage()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
    }

    public static DAOmilage getInstance()
    {
        if (instance == null) {
            instance = new DAOmilage();
        }
        return instance;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    DocumentReference fireStore;
    String userID;
    Milage milage;

    public void addMilage(HashMap<String, Object> milage){
        db.collection("milages")
                .add(milage)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG","DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public MilageManager getMilageOfOneDay(Date date){
        MilageManager milageManager = new MilageManager();
        CollectionReference milageRef = db.collection("milages");
        milageRef.whereEqualTo("user", userID);
        milageRef.whereEqualTo("date", date);
        Query query = milageRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        Milage milage = new Milage(document.getString("user"),document.getString("date"),
                                document.getString("start_location"), document.getString("end_location"),
                                document.getString("reg_number"), Integer.parseInt(document.getString("kilometer")));
                        milageManager.addMilage(milage);
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return milageManager;
    }

    public MilageManager getAllMilage(){
        MilageManager milageManager = new MilageManager();
        CollectionReference milageRef = db.collection("milages");
        milageRef.whereEqualTo("user", userID);
        Query query =  milageRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        Milage milage = new Milage(document.getString("user"),document.getString("date"),
                                document.getString("start_location"), document.getString("end_location"),
                                document.getString("reg_number"), Integer.parseInt(document.getString("kilometer")));
                        milageManager.addMilage(milage);
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return milageManager;
    }

}
