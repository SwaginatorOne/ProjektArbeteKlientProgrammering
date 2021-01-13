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

import com.example.Tajming.java.WorkShift;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeManagerActivity extends AppCompatActivity
{
    Button button_start_day;
    Button button_continue_day;
    Button button_start_break;
    Button button_end_day;
    TextView textView_started_working;
    TextView textView_started_break;
    TextView textView_show_started_working;
    TextView textView_show_break;
    TextView textField_show_status;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dayFormat = new SimpleDateFormat("E, dd MMM", Locale.ENGLISH);
    Date date = new Date();
    boolean currentDay = false;
    int countBreakClicks = 0;
    private int seconds = 0;
    private Boolean running = false;
    private int totalBreakSec;
    private int totalBreakMin;
    private int totalBreakHour;
    private String sb;
    String userID;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_manager);

        if (savedInstanceState != null)
        {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        button_start_day = findViewById(R.id.button_start_day_time);
        button_continue_day = findViewById(R.id.button_continue_day_time);
        button_start_break = findViewById(R.id.button_start_break_time);
        button_end_day = findViewById(R.id.button_stop_day);
        textView_started_working = findViewById(R.id.textField_start_working_time);
        textView_started_break = findViewById(R.id.textField_break_time);
        textView_show_started_working = findViewById(R.id.textField_show_start_working_time);
        textView_show_break = findViewById(R.id.textField_show_break_time);
        textField_show_status = findViewById(R.id.textField_show_status_time);

        button_continue_day.setVisibility(View.GONE);
        button_end_day.setVisibility(View.GONE);
        button_start_break.setVisibility(View.GONE);
        textView_started_working.setVisibility(View.GONE);
        textView_started_break.setVisibility(View.GONE);
        textView_show_started_working.setVisibility(View.GONE);
        textView_show_break.setVisibility(View.GONE);

        String current_day = dayFormat.format(date);
        WorkShift workShift = new WorkShift();
        workShift.setDate(current_day);
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        workShift.setUser(userID);

        runTimer();

        button_start_day.setOnClickListener(new View.OnClickListener()
        {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
                if (currentDay != true)
                {
                    button_start_day.setVisibility(View.GONE);
                    button_end_day.setVisibility(View.VISIBLE);
                    button_start_break.setVisibility(View.VISIBLE);
                    textView_started_working.setVisibility(View.VISIBLE);
                    textView_show_started_working.setVisibility(View.VISIBLE);
                    textField_show_status.setText("Working");
                    String start_Time = timeFormat.format(Calendar.getInstance().getTime());
                    workShift.setStartTime(start_Time);
                    textView_show_started_working.setText(start_Time);
                    workShift.setStart_time_calculator(LocalTime.now());
                    Toast.makeText(TimeManagerActivity.this, "Enjoy your day", Toast.LENGTH_SHORT).show();

                }

            }
        });

        button_start_break.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                button_start_day.setVisibility(View.GONE);
                button_continue_day.setVisibility(View.VISIBLE);
                button_end_day.setVisibility(View.VISIBLE);
                button_start_break.setVisibility(View.GONE);
                textView_started_break.setVisibility(View.VISIBLE);
                textView_show_break.setVisibility(View.VISIBLE);
                textField_show_status.setText("Taking a break");

                countBreakClicks++;
                if (countBreakClicks%2 == 0)
                {
                    running = false;
                  //  totalBreakHour = totalBreakHour + seconds/3600;
                   // totalBreakMin = totalBreakMin + (seconds%3600)/60;
                   // totalBreakSec = totalBreakSec + seconds%60;
                   // sb =totalBreakHour + "h, " +  totalBreakMin + "min, " + totalBreakSec + "sec";
                }
                else
                    {
                    running = true;
                }
            }
        });

        button_continue_day.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                button_start_day.setVisibility(View.GONE);
                button_continue_day.setVisibility(View.GONE);
                button_end_day.setVisibility(View.VISIBLE);
                button_start_break.setVisibility(View.VISIBLE);
                textView_started_break.setVisibility(View.INVISIBLE);
                textView_show_break.setVisibility(View.INVISIBLE);
                textField_show_status.setText("Working");
            }
        });

        button_end_day.setOnClickListener(new View.OnClickListener()
        {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
                button_start_day.setVisibility(View.VISIBLE);
                button_continue_day.setVisibility(View.GONE);
                button_end_day.setVisibility(View.GONE);
                button_start_break.setVisibility(View.GONE);
                textView_started_working.setVisibility(View.GONE);
                textView_started_break.setVisibility(View.GONE);
                textView_show_started_working.setVisibility(View.GONE);
                textView_show_break.setVisibility(View.GONE);
                textField_show_status.setText("Done working for today");

                if (currentDay == true)
                {
                   currentDay = false;
                    String stop_time = timeFormat.format(Calendar.getInstance().getTime());
                    workShift.setEndTime(stop_time);
                    workShift.setBreakTime(sb);
                   // workShift.addToDataBase();
                    workShift.setStop_time_calculator(LocalTime.now());
                    Toast.makeText(TimeManagerActivity.this, "Good job, enjoy your freedom", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    private void runTimer()
    {
        final Handler handler = new Handler();
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, secs);
                textView_show_break.setText(time);
                if (running)
                {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
