package com.example.Tajming;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public abstract class Time {

    public int getHour(int seconds){
        return seconds/3600;
    }
    public int getMinutes(int seconds){
        return (seconds%3600)/60;
    }
    public int getSeconds(int seconds){
        return seconds%60;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getMonth(){
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        return month+1;
    }
}
