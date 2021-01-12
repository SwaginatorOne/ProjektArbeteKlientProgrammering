package com.example.Tajming.java;

import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MilageManager {

    private List<Milage> milageList;
    private String totalTimeMonth;
    private String stringHour;
    private String stringMin;
    private String stringSec;
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
        for(Milage milage : milageList){
            if (month == "jan"){
                if(milage.getDate().substring(8) == "Jan"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "feb"){
                if(milage.getDate().substring(8) == "Feb"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "mar"){
                if(milage.getDate().substring(8) == "Mar"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "apr"){
                if(milage.getDate().substring(8) == "Apr"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "may"){
                if(milage.getDate().substring(8) == "May"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "jun"){
                if(milage.getDate().substring(8) == "Jun"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "jul"){
                if(milage.getDate().substring(8) == "Jul"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "aug"){
                if(milage.getDate().substring(8) == "Aug"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "sep"){
                if(milage.getDate().substring(8) == "Sep"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "oct"){
                if(milage.getDate().substring(8) == "Oct"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "nov"){
                if(milage.getDate().substring(8) == "Nov"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }else if(month == "dec"){
                if(milage.getDate().substring(8) == "Dec"){
                    totalKilometer = totalKilometer + milage.getKilometer();
                }
            }
        }
        return (double) totalKilometer/10;
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
