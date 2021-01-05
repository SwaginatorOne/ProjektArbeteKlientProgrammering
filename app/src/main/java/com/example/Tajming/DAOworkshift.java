package com.example.Tajming;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DAOworkshift {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;

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

    public void editWorkShiftInDatabase(){

    }
    public void getWorkShift(){

    }

    public void getAllWorkShiftsPerMonth(){

    }

    public String getCurrentUser(){
        return firebaseAuth.getCurrentUser().getUid();
    }
}
