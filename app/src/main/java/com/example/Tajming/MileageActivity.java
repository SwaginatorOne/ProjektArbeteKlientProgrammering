package com.example.Tajming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MileageActivity extends AppCompatActivity
{
    SimpleDateFormat dayFormat = new SimpleDateFormat("E, dd MMM", Locale.ENGLISH);
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    TextView textView_current_day_mileage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);

        textView_current_day_mileage = findViewById(R.id.textField_current_day_mileage);
        String current_day = dayFormat.format(date);
        textView_current_day_mileage.setText(current_day);

    }
}