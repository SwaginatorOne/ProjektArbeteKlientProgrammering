package com.example.Tajming;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
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
    TextView textView_start_time;
    TextView textView_stop_time;
    TextView textView_hours_worked;
    TextView textView_current_day;
    Instant start_time_calculator;
    Instant stop_time_calculator;
    Duration timeElapsed;
    boolean currentDay = false;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dayFormat = new SimpleDateFormat("E, dd MMM", Locale.ENGLISH);
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_manager);

        button_start_time = findViewById(R.id.button_start_time);
        button_stop_time = findViewById(R.id.button_stop_time);
        textView_start_time = findViewById(R.id.textView_starttime);
        textView_stop_time = findViewById(R.id.textView_stop_time);
        textView_hours_worked = findViewById(R.id.textView_hours_worked_calculated);
        textView_current_day = findViewById(R.id.textField_current_day_time);

        String current_day = dayFormat.format(date);
        textView_current_day.setText(current_day);

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
                    currentDay = true;
                    String start_Time = timeFormat.format(calendar.getTime());
                    textView_start_time.setText(start_Time);
                    start_time_calculator = Instant.now();
                    Toast.makeText(TimeManagerActivity.this, "Enjoy your day", Toast.LENGTH_SHORT).show();
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
                    currentDay = false;
                    String stop_time = timeFormat.format(calendar.getTime());
                    textView_stop_time.setText(stop_time);
                    stop_time_calculator = Instant.now();
                    timeElapsed = Duration.between(start_time_calculator, stop_time_calculator);
                    textView_hours_worked.setText(timeElapsed.toString());
                    Toast.makeText(TimeManagerActivity.this, "Good job, enjoy your freedom", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
