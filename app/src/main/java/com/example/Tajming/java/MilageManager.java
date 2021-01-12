package com.example.Tajming.java;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MilageManager {

    private List<Milage> milageList;
    private int totalKilometer;
    Double totalMile;

    public MilageManager(){
        milageList = new ArrayList<Milage>();
    }

    public MilageManager(ArrayList<Milage> milages){
        milageList = milages;
    }

    public void addMilage(Milage milage){
        milageList.add(milage);
    }

    public double getTotalMilageMonth(String month){
        totalKilometer = 0;
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
        for(Milage milage : milageList){
            if(milage.getDate().substring(8) == monthInDate){
                totalKilometer = totalKilometer + milage.getKilometer();
            }
        }
        totalMile = (double) totalKilometer/10;
        return totalMile;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public double getTotalMilageWeek(int weekNumber){
        totalKilometer = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.WEEK_OF_YEAR, weekNumber);
        for(int i = 0; i< 7; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            String date = (sdf.format(cal.getTime()));
            for(Milage milage : milageList){
                if(milage.getDate() == date){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }
        }
        totalMile = (double) totalKilometer/10;
        return totalMile;
    }

    public double getTotalMilageDay() {
        totalKilometer = 0;
        for(Milage milage : milageList){
            totalKilometer = totalKilometer + milage.getKilometer();
        }
        totalMile = (double) totalKilometer/10;
        return totalMile;
    }
}
