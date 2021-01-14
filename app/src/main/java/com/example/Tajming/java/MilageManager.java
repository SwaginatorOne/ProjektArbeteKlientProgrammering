package com.example.Tajming.java;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double getTotalMilageMonth(LocalDate date){
        totalKilometer = 0;
        int month = date.getDayOfMonth();
        String monthInDate = null;
        if (month == 0){
            monthInDate = "Jan";
        }else if(month == 1){
            monthInDate = "Feb";
        }else if(month == 2){
            monthInDate = "Mar";
        }else if(month == 3){
            monthInDate = "Apr";
        }else if(month == 4){
            monthInDate = "May";
        }else if(month == 5){
            monthInDate = "Jun";
        }else if(month == 6){
            monthInDate = "Jul";
        }else if(month == 7){
            monthInDate = "Aug";
        }else if(month == 8){
            monthInDate = "Sep";
        }else if(month == 9){
            monthInDate = "Oct";
        }else if(month == 10){
            monthInDate = "Nov";
        }else if(month == 11){
            monthInDate = "Dec";
        }
        for(Milage milage : milageList){
            if(milage.getDate().substring(0,3) == monthInDate){
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
