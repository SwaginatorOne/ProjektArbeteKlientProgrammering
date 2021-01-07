package com.example.Tajming;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.Tajming.fragments.MonthFragment;
import com.example.Tajming.fragments.TodayFragment;
import com.example.Tajming.fragments.WeekFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class ReportActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

       bottomNavigationView = findViewById(R.id.navigation_bar_bottom_report);
       bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodayFragment()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
        {
            Fragment fragment = null;

            switch (menuItem.getItemId())
            {
                case R.id.navigation_bar_today:
                fragment = new TodayFragment();
                break;

                case R.id.navigation_bar_week:
                    fragment = new WeekFragment();
                    break;

                case R.id.navigation_bar_month:
                    fragment = new MonthFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
    };
}