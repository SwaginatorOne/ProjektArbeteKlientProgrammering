package com.example.Tajming;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;

public class TimeManagerActivity extends AppCompatActivity
{
    Button button_start_time;
    Button button_stop_time;
    TextView textView_start_time;
    TextView textView_stop_time;
    TextView textView_hours_worked;
    Instant start_time_calculator;
    Instant stop_time_calculator;
    Duration timeElapsed;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_manager);

        button_start_time = findViewById(R.id.button_start_time);
        button_stop_time = findViewById(R.id.button_stop_time);
        textView_start_time = findViewById(R.id.textView_start_time);
        textView_stop_time = findViewById(R.id.textView_stop_time);
        textView_hours_worked = findViewById(R.id.textView_hours_worked_calculated);

        button_start_time.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
                button_start_time.setClickable(false);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat timeFormat =  new SimpleDateFormat("HH:mm:ss");
                String start_Time = timeFormat.format(calendar.getTime());
                textView_start_time.setText(start_Time);
                start_time_calculator = Instant.now();
            }
        });

        button_stop_time.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
             button_stop_time.setClickable(false);
             Calendar calendar = Calendar.getInstance();
             SimpleDateFormat timeFormat =  new SimpleDateFormat("HH:mm:ss");
             String stop_time = timeFormat.format(calendar.getTime());
             textView_stop_time.setText(stop_time);
             stop_time_calculator = Instant.now();
             timeElapsed = Duration.between(start_time_calculator, stop_time_calculator);
             textView_hours_worked.setText((int) (timeElapsed.toHours()+timeElapsed.toMinutes()));
            }
        });
    }
}
