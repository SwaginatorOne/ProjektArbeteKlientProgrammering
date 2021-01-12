package com.example.Tajming.java;

import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class MilageManager {

    private List<Milage> milageList;
    private String totalTimeMonth;
    private String stringHour;
    private String stringMin;
    private String stringSec;


    public MilageManager(){
        milageList = new ArrayList<Milage>();
    }

    public MilageManager(ArrayList<Milage> milages){
        milageList = milages;
    }

    public void addMilage(Milage milage){
        milageList.add(milage);
    }

    public String getTotalMilgaeMonth(){
        return null;
    }

    public String getTotalMilageDay() {
        return null;
    }
}
