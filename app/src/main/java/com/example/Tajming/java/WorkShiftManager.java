package com.example.Tajming.java;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.Tajming.java.WorkShift;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    public String getTotalTimeMonth(String month){
        String monthInDate = null;
        if (month == "jan"){
            monthInDate = "Jan";
        }else if(month == "feb"){
            monthInDate = "Feb";
        }else if(month == "mar"){
            monthInDate = "Mar";
        }else if(month == "apr"){
            monthInDate = "Apr";
        }else if(month == "may"){
            monthInDate = "May";
        }else if(month == "jun"){
            monthInDate = "Jun";
        }else if(month == "jul"){
            monthInDate = "Jul";
        }else if(month == "aug"){
            monthInDate = "Aug";
        }else if(month == "sep"){
            monthInDate = "Sep";
        }else if(month == "oct"){
            monthInDate = "Oct";
        }else if(month == "nov"){
            monthInDate = "Nov";
        }else if(month == "dec"){
            monthInDate = "Dec";
        }

        Duration totalDur = Duration.ZERO;
        for(WorkShift workShift:workShiftList){
            if(workShift.getDate().substring(8) == monthInDate)
                totalDur = totalDur.plus(workShift.calculateWorkTimeToDuration());
        }
        String time = String.format("%d:%02d:%02d",
                totalDur.toHours(),
                totalDur.toMinutes(),
                totalDur.getSeconds());
        return time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTotalTimeWeek(int weekNumber){
        Duration totalDur = Duration.ZERO;
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.WEEK_OF_YEAR, weekNumber);
        for(int i = 0; i< 7; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            String date = (sdf.format(cal.getTime()));
            for(WorkShift workShift : workShiftList){
                if(workShift.getDate() == date){
                    totalDur = totalDur.plus(workShift.calculateWorkTimeToDuration());
                }
            }
        }
        String time = String.format("%d:%02d:%02d",
                totalDur.toHours(),
                totalDur.toMinutes(),
                totalDur.getSeconds());
        return time;
    }
}
