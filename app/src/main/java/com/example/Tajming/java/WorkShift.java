package com.example.Tajming.java;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class WorkShift {

    private String user;
    private String date;
    private String startTime;
    private String endTime;
    private String breakTime;
    private Map<String, Object> workShift;
    FirebaseFirestore db;
    private Instant start_time_calculator;
    private Instant stop_time_calculator;
    private Duration timeElapsed;

    public WorkShift(){
       db = FirebaseFirestore.getInstance();
        workShift = new HashMap<>();
    }
    public WorkShift(String user, String date, String startTime, String endTime, String breakTime) {
        this.user = user;
        this. date = date;
        this.startTime = startTime;
        this. endTime = endTime;
        this.breakTime = breakTime;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public Map<String, Object> getHashMap(){
        workShift.put("user", user);
        workShift.put("start_time", startTime);
        workShift.put("end_time", endTime);
        workShift.put("date", date);
        workShift.put("break", breakTime);
        return workShift;
    }

    public String getUser(){
        return user;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getBreakTime() {
        return breakTime;
    }
    public Instant getStart_time_calculator() {
        return start_time_calculator;
    }

    public void setStart_time_calculator(Instant start_time_calculator) {
        this.start_time_calculator = start_time_calculator;
    }

    public Instant getStop_time_calculator() {
        return stop_time_calculator;
    }

    public void setStop_time_calculator(Instant stop_time_calculator) {
        this.stop_time_calculator = stop_time_calculator;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateWorkTimeToString(){
        timeElapsed = Duration.between(start_time_calculator, stop_time_calculator);
        String time = String.format("%d:%02d:%02d",
                timeElapsed.toHours(),
                timeElapsed.toMinutes(),
                timeElapsed.getSeconds());
        return time;
    }

    public void addToDataBase(){
        workShift.put("user", user);
        workShift.put("start_time", startTime);
        workShift.put("end_time", endTime);
        workShift.put("date", date);
        workShift.put("break", breakTime);

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