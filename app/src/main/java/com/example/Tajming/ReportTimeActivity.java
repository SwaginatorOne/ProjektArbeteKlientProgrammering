package com.example.Tajming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class ReportTimeActivity extends AppCompatActivity
{
    private TimePicker timePicker_start_work;
    private TimePicker timePicker_stop_work;
    private TimePicker timePicker_start_break;
    private TimePicker timePicker_stop_break;
    private Button button_save_time_changes;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_time);
       /* Intent intent = getIntent();
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int dayOfMonth = intent.getIntExtra("dayOfMonth", 0);
        TextView pickedDate = (TextView) findViewById(R.id.textField_picked_date);
        pickedDate.setText(year + ":" + month +":" + dayOfMonth);

        */

        timePicker_start_work = findViewById(R.id.timePicker_start_working);
        timePicker_stop_work = findViewById(R.id.timePicker_stop_working);
        timePicker_start_break = findViewById(R.id.timePicker_start_break);
        timePicker_stop_break = findViewById(R.id.timePicker_stop_break);
        button_save_time_changes = findViewById(R.id.button_edit_day_save);


        timePicker_start_work.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                date = String.valueOf(hourOfDay).toString() + ":" + String.valueOf(minute).toString();
            }
        });

        timePicker_stop_work.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                date = String.valueOf(hourOfDay).toString() + ":" + String.valueOf(minute).toString();
            }
        });

        timePicker_start_break.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                date = String.valueOf(hourOfDay).toString() + ":" + String.valueOf(minute).toString();
            }
        });

        timePicker_stop_break.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                date = String.valueOf(hourOfDay).toString() + ":" + String.valueOf(minute).toString();
            }
        });

        button_save_time_changes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }
}