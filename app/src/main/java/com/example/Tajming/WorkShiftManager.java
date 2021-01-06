package com.example.Tajming;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class WorkShiftManager {

    private List<WorkShift> workShiftList;
    private String totalTimeMonth;
    private String stringHour;
    private String stringMin;
    private String stringSec;
    private int hour;
    private int min;
    private int sec;

    public WorkShiftManager(){
        workShiftList = new ArrayList<WorkShift>();
    }

    public WorkShiftManager(ArrayList<WorkShift> workShiftArrayList){
        workShiftList = workShiftArrayList;
    }

    public void addWorkShift(WorkShift workShift){
        workShiftList.add(workShift);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTotalTimeMonth(){
        for(WorkShift workShift:workShiftList){
            String time = workShift.calculateWorkTimeToString();
            time.replace(":", "");
            stringHour = time.substring(0,2);
            stringMin = time.substring(2,4);
            stringSec = time.substring(4);
            hour = hour + Integer.parseInt(stringHour);
            min = min + Integer.parseInt(stringMin);
            sec = sec + Integer.parseInt(stringSec);
        }
        totalTimeMonth = hour + "h " + min + "min " + sec + "sec";
        return totalTimeMonth;
    }
}
