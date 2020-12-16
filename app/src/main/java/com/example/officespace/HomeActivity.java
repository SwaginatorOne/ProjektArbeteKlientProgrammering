package com.example.officespace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity
{
    private Button timeButton;
    private Button profileButton;
//hej jag heter bengt och jag jobbar i en knappfabrik
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        timeButton = findViewById(R.id.menu_time);
        profileButton = findViewById(R.id.menu_profile);

        timeButton.setOnClickListener(v ->
        {
            Intent intent = new Intent (HomeActivity.this, TimeManagerActivity.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(v ->
        {
            Intent intent = new Intent (HomeActivity.this, ProfileManagerActivity.class);
            startActivity(intent);
        });
    }
}