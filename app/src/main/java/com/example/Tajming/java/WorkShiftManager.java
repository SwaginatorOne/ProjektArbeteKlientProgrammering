package com.example.Tajming.java;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.Tajming.java.WorkShift;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WorkShiftManager {

    public List<WorkShift> workShiftList;
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

    public WorkShiftManager(List<WorkShift> workShiftArrayList){
        workShiftList = workShiftArrayList;
    }

    public void addWorkShift(WorkShift workShift){
        workShiftList.add(workShift);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTotalTimeMonth(int month){
        String monthInDate = null;
        if (month == 1){
            monthInDate = "Jan";
        }else if(month == 2){
            monthInDate = "Feb";
        }else if(month == 3){
            monthInDate = "Mar";
        }else if(month == 4){
            monthInDate = "Apr";
        }else if(month == 5){
            monthInDate = "May";
        }else if(month == 6){
            monthInDate = "Jun";
        }else if(month == 7){
            monthInDate = "Jul";
        }else if(month == 8){
            monthInDate = "Aug";
        }else if(month == 9){
            monthInDate = "Sep";
        }else if(month == 10){
            monthInDate = "Oct";
        }else if(month == 11){
            monthInDate = "Nov";
        }else if(month == 12){
            monthInDate = "Dec";
        }
        Duration totalDur = Duration.parse("PT0S");
        for(WorkShift workShift:workShiftList){
            if(workShift.getDate().substring(0, 3).equals(monthInDate)) {
                totalDur = totalDur.plus(workShift.calculateWorkTimeToDuration());
            }
        }
        String time = String.format("%d:%02d",
                totalDur.toHours(),
                totalDur.toMinutes());
        return time;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTotalBreakTimeMonth(int month){
        String monthInDate = null;
        if (month == 1){
            monthInDate = "Jan";
        }else if(month == 2){
            monthInDate = "Feb";
        }else if(month == 3){
            monthInDate = "Mar";
        }else if(month == 4){
            monthInDate = "Apr";
        }else if(month == 5){
            monthInDate = "May";
        }else if(month == 6){
            monthInDate = "Jun";
        }else if(month == 7){
            monthInDate = "Jul";
        }else if(month == 8){
            monthInDate = "Aug";
        }else if(month == 9){
            monthInDate = "Sep";
        }else if(month == 10){
            monthInDate = "Oct";
        }else if(month == 11){
            monthInDate = "Nov";
        }else if(month == 12){
            monthInDate = "Dec";
        }
        Duration totalBreakDur = Duration.parse("PT0S");
        for(WorkShift workShift:workShiftList){
            if(workShift.getDate().substring(0, 3).equals(monthInDate)) {
                totalBreakDur = totalBreakDur.plus(workShift.getBreakTimeDur());
            }
        }
        String time = String.format("%d:%02d",
                totalBreakDur.toHours(),
                totalBreakDur.toMinutes());
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
                    Duration temp = workShift.calculateWorkTimeToDuration();
                    totalDur = totalDur.plus(temp);
                }
            }
        }
        String time = String.format("%d:%02d:%02d",
                totalDur.toHours(),
                totalDur.toMinutes(),
                totalDur.getSeconds());
        return time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTotalTimeDay(String date){
        Duration totalDurr = Duration.parse("PT0S");
        for(WorkShift workShift:workShiftList){
            if(workShift.getDate().equals(date)) {
                totalDurr = totalDurr.plus(workShift.calculateWorkTimeToDuration());
            }
        }
        String time = String.format("%d:%02d",
                totalDurr.toHours(),
                totalDurr.toMinutes());
        return time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<WorkShift> getMonthList(int month){
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
        ArrayList<WorkShift> list = new ArrayList<WorkShift>();
        for (WorkShift workShift:workShiftList){
            if(workShift.getDate().substring(0,3) == monthInDate){
                list.add(workShift);
            }
        }
        return list;
    }
}
