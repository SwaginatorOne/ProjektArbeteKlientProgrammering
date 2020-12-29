package com.example.Tajming;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeManagerActivity extends AppCompatActivity
{
    Button button_start_time;
    Button button_stop_time;
    Button button_start_break;
    TextView textView_start_time;
    TextView textView_start_break;
    TextView textView_stop_time;
    TextView textView_hours_worked;
    TextView textView_current_day;
    Instant start_time_calculator;
    Instant stop_time_calculator;
    Duration timeElapsed;
    boolean currentDay = false;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dayFormat = new SimpleDateFormat("E, dd MMM", Locale.ENGLISH);
    Date date = new Date();
    String userID;
    int countBreakClicks = 0;
    private int seconds = 0;
    private Boolean running = false;
    private int totalBreakMin;
    private int totalBreakSec;
    private int totalBreakHour;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_manager);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        button_start_time = findViewById(R.id.button_start_time);
        button_stop_time = findViewById(R.id.button_stop_time);
        button_start_break = findViewById(R.id.button_start_break);
        textView_start_time = findViewById(R.id.textView_starttime);
        textView_start_break = findViewById(R.id.textView_start_break);
        textView_stop_time = findViewById(R.id.textView_stop_time);
        textView_hours_worked = findViewById(R.id.textView_hours_worked_calculated);
        textView_current_day = findViewById(R.id.textField_current_day_time);
        String current_day = dayFormat.format(date);
        textView_current_day.setText(current_day);
        WorkShift workShift = new WorkShift();
        workShift.setDate(current_day);


        runTimer();

        button_start_time.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
                if (currentDay != true)
                {
                    button_start_time.setClickable(false);
                    button_stop_time.setClickable(true);
                    button_start_break.setClickable(true);
                    currentDay = true;
                    String start_Time = timeFormat.format(Calendar.getInstance().getTime());
                    workShift.setStartTime(start_Time);
                    textView_start_time.setText(start_Time);
                    start_time_calculator = Instant.now();
                    Toast.makeText(TimeManagerActivity.this, "Enjoy your day", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_start_break.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBreakClicks++;
                if (countBreakClicks%2 == 0){
                    button_stop_time.setClickable(true);
                    button_start_break.setText("Take break");
                    running = false;
                    totalBreakSec = totalBreakSec + seconds%60;
                    String sb = totalBreakSec + " sec";
                    TextView breakCalculated = (TextView) findViewById(R.id.textView_break_taken_calculated);
                    breakCalculated.setText(sb);

                }else{
                    button_stop_time.setClickable(false);
                    button_start_break.setText("Stop break");
                    running = true;
                }
            }
        });

        button_stop_time.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
                if (currentDay == true)
                {
                    button_stop_time.setClickable(false);
                    button_start_time.setClickable(true);
                    button_start_break.setClickable(false);
                    currentDay = false;
                    String stop_time = timeFormat.format(Calendar.getInstance().getTime());
                    workShift.setEndTime(stop_time);
                    textView_stop_time.setText(stop_time);
                    stop_time_calculator = Instant.now();
                    timeElapsed = Duration.between(start_time_calculator, stop_time_calculator);
                    textView_hours_worked.setText(timeElapsed.toString());
                    Toast.makeText(TimeManagerActivity.this, "Good job, enjoy your freedom", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d", hours, minutes);
                textView_start_break.setText(minutes + ":" + secs);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
