package com.example.Tajming.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.Tajming.WorkShift;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executor;

public class DAOworkshift {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    DocumentReference fireStore;
    String userID;
    WorkShift workShift;


    public void addWorkShiftToDatabase(HashMap<String, Object> workShift){
        db.collection("work_shifts")
                .add(workShift)
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

    public void editWorkShift(WorkShift workShift, Date date){
        final String[] id = new String[1];
        CollectionReference workshiftRef = db.collection("work_shifts");
        workshiftRef.whereEqualTo("date", date);
        workshiftRef.whereEqualTo("user", userID);
        Query query = workshiftRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        id[0] = document.getId();
                    }
                }
                else{
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        DocumentReference documentReference = db.collection("work_shifts").document(id[0]);
        documentReference.update(workShift.getHashMap());
    }

    public WorkShift getWorkShift(Date date){
        CollectionReference workshiftRef = db.collection("work_shifts");
        workshiftRef.whereEqualTo("user", userID);
        workshiftRef.whereEqualTo("date", date);
        Query query = workshiftRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        WorkShift workShift = new WorkShift(document.getString("user"),document.getString("date"),
                                document.getString("start_time"), document.getString("end_time"),
                                document.getString("break"));
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return workShift;
    }

    public WorkShiftManager getAllWorkShifts(){
        WorkShiftManager workShiftManager = new WorkShiftManager();
        CollectionReference workShiftRef = db.collection("work_shifts");
        workShiftRef.whereEqualTo("user", userID);
        Query query =  workShiftRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        WorkShift workShift = new WorkShift(document.getString("user"),document.getString("date"),
                                document.getString("start_time"), document.getString("end_time"),
                                document.getString("break"));
                        workShiftManager.addWorkShift(workShift);
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return workShiftManager;
    }

    public String getCurrentUser(){
        return firebaseAuth.getCurrentUser().getUid();
    }
}
