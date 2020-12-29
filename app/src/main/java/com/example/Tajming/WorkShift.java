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
    int breakTime;
    Map<String, Object> workShift;

    public WorkShift(){
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
    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
        workShift.put("break", breakTime);
    }

    public Map<String, Object> getHashMap(){
        return workShift;
    }
}
