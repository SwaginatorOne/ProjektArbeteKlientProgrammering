package com.example.Tajming;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.Tajming.fragments.MonthFragment;
import com.example.Tajming.fragments.DayFragment;
import com.example.Tajming.fragments.WeekFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


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
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DayFragment()).commit();


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
                fragment = new DayFragment();
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