package com.example.Tajming;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WorkShift {
    String user;
    String date;
    String startTime;
    String endTime;
    String breakTime;
    Map<String, Object> workShift;
    FirebaseFirestore db;

    public WorkShift(){
       db = FirebaseFirestore.getInstance();
        workShift = new HashMap<>();
    }
    public void setUser(String user) {
        this.user = user;
        workShift.put("user", user);
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
        workShift.put("start_time", startTime);
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
        workShift.put("end_time", endTime);
    }
    public void setDate(String date) {
        this.date = date;
        workShift.put("date", date);
    }
    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
        workShift.put("break", breakTime);
    }

    public Map<String, Object> getHashMap(){
        return workShift;
    }

    public void addToDataBase(){
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
}
