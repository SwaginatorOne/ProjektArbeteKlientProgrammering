package com.example.Tajming.java;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Milage {
    private String date;
    private Date datecal;
    private String startLocation;
    private String endLocation;
    private String regNumber;
    private String userID;
    private Map<String, Object> milage;

    public Milage(){

    }
    public Milage(String userID, Date date, String startLocation, String endLocation, String regNumber, int kilometer){
        this.userID = userID;
        this.datecal = date;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.regNumber = regNumber;
        this.kilometer = kilometer;
        milage = new HashMap<String, Object>();
    }

    public Map<String, Object> getHashMap(){
        milage.put("date", date);
        milage.put("start_location", startLocation);
        milage.put("end_location", endLocation);
        milage.put("reg_number", regNumber);
        milage.put("kilometer", kilometer);
        milage.put("user", userID);
        return milage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public int getKilometer() {
        return kilometer;
    }

    public void setKilometer(int kilometer) {
        this.kilometer = kilometer;
    }

    private int kilometer;


}
