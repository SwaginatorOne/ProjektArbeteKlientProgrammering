package com.example.Tajming.java;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WorkShift {

    private String user;
    private String date;
    private String startTime;
    private String endTime;
    private String breakTime;
    private Map<String, Object> workShift;
    FirebaseFirestore db;
    private LocalTime start_time_calculator;
    private LocalTime end_time_calculator;
    public LocalTime break_start_time_calculator;
    public LocalTime break_end_time_calculator;
    private String totalTimeString;
    private Duration timeElapsed;
    private Duration totalBreakTime = Duration.ZERO;

    public WorkShift(){
       db = FirebaseFirestore.getInstance();
       workShift = new HashMap<String, Object>();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public WorkShift(String user, String date, String startTime, String endTime, String breakTime) {
        workShift = new HashMap<String, Object>();
        this.user = user;
        this. date = date;
        this.startTime = startTime;
        start_time_calculator = LocalTime.parse(startTime);
        this. endTime = endTime;
        end_time_calculator = LocalTime.parse(endTime);
        if(breakTime.equals("0:0")){
            this.totalBreakTime = Duration.parse("PT0S");
        }else{
            this.totalBreakTime = Duration.parse(breakTime);
        }
    }
    public void setUser(String user) {
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setStartTime(String startTime) {
        this.startTime = startTime;
        totalBreakTime = Duration.parse("PT0S");
        start_time_calculator = LocalTime.parse(startTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setEndTime(String endTime) {
        this.endTime = endTime;
        end_time_calculator = LocalTime.parse(endTime);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBreakTime(String breakTime) {

        this.breakTime = breakTime;
        System.out.println(this.breakTime);
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

    public Duration getBreakTimeDur() {
        return totalBreakTime;
    }
    public String getBreakTime() {
        String totTimeSting = totalBreakTime.toHours() + ":" + totalBreakTime.toMinutes();
        return totTimeSting;
    }
    public LocalTime getStart_time_calculator() {
        return start_time_calculator;
    }

    public void setStart_time_calculator(LocalTime start_time_calculator) {
        this.start_time_calculator = start_time_calculator;
    }

    public void setStop_time_calculator(LocalTime stop_time_calculator) {
        this.end_time_calculator = stop_time_calculator;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBreakStartTime(String startBreakTime){
        //System.out.println("BREAK: "+ startBreakTime);
        this.break_start_time_calculator = LocalTime.parse(startBreakTime);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBreakEndTime(String endBreakTime){
        this.break_end_time_calculator = LocalTime.parse(endBreakTime);
        totalBreakTime = totalBreakTime.plus(Duration.between(break_start_time_calculator, break_end_time_calculator));
        breakTime = totalBreakTime.toString();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void calculateBreak(){
        totalBreakTime.plus(Duration.between(break_start_time_calculator,break_end_time_calculator));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateWorkTimeToString(){
        timeElapsed = Duration.between(start_time_calculator, end_time_calculator);
        timeElapsed.minus(totalBreakTime);
        System.out.println(timeElapsed);
        String time = String.format("%d:%02d",
                timeElapsed.toHours(),
                timeElapsed.toMinutes());
        System.out.println("Time: "+  time);
        return time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Duration calculateWorkTimeToDuration(){
        timeElapsed = Duration.between(start_time_calculator, end_time_calculator);
        return timeElapsed;
    }
    public Duration calculateBreakTimeToDuration(){
        System.out.println("break: " + break_start_time_calculator + "  " + break_end_time_calculator);
        totalBreakTime = Duration.between(break_start_time_calculator, break_end_time_calculator);
        System.out.println(totalBreakTime);
        return totalBreakTime;
    }

    public String calcTime() throws ParseException {
        String s = startTime.replace(":","");
        String e = endTime.replace(":", "");
        int hour = Integer.parseInt(s.substring(0,2) + Integer.parseInt(e.substring(0,2)));
        int min = Integer.parseInt(s.substring(2,4)+ Integer.parseInt(e.substring(2,4)));
        int sec = Integer.parseInt("00");
        if (sec >= 60) {
            min ++;
            sec = sec % 60;
        }
        if (min >= 60) {
            hour ++;
            min = min % 60;
        }
        return hour +":"+min+":"+sec;
    }
}
