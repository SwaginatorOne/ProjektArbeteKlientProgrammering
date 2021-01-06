package com.example.Tajming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Tajming.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
{

    private Button button_time_home;
    private Button button_profile_home;
    private Button button_mileage_home;
    private Button button_logout_home;
    private Button button_report_home;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button_time_home = findViewById(R.id.menu_time);
        button_profile_home = findViewById(R.id.menu_profile);
        button_mileage_home = findViewById(R.id.menu_mileage);
        button_logout_home = findViewById(R.id.button_logout_home);
        button_report_home = findViewById(R.id.menu_report);

        button_time_home.setOnClickListener(v ->
        {
            Intent intent = new Intent (HomeActivity.this, TimeManagerActivity.class);
            startActivity(intent);
        });

        button_profile_home.setOnClickListener(v ->
        {
            Intent intent = new Intent (HomeActivity.this, ProfileManagerActivity.class);
            startActivity(intent);
        });

        button_mileage_home.setOnClickListener(v ->
        {
            Intent intent = new Intent(HomeActivity.this, MileageActivity.class);
            startActivity(intent);
        });

        button_report_home.setOnClickListener(v ->
        {
            Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
            startActivity(intent);
        });

        button_logout_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}