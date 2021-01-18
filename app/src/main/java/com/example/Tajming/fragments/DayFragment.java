package com.example.Tajming.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tajming.R;
import com.example.Tajming.ReportTimeActivity;
import com.example.Tajming.java.WorkShift;
import com.example.Tajming.java.WorkShiftManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class DayFragment extends Fragment
{
    Button button_change_day;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String userID;
    TextView totalTime;
    TextView totalBreak;
    String dayTime;
    String breakTime;
    SimpleDateFormat dayFormat = new SimpleDateFormat("MMM d, yyyy");
    Date dateE = new Date();

    List<WorkShift> ws = new ArrayList<WorkShift>();
    WorkShiftManager workShiftManager = new WorkShiftManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);
        button_change_day = (Button) rootView.findViewById(R.id.button_edit_day);
        totalTime = rootView.findViewById(R.id.textField_hours_worked_report_show_day);
        totalBreak = rootView.findViewById(R.id.textField_break_taken_report_show_day);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        db.collection("work_shifts")
                .whereEqualTo("user", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                Log.d("TAG", documentSnapshots.getId() + " => " + documentSnapshots.getData());
                                String user = documentSnapshots.getString("user");
                                String date = documentSnapshots.get("date").toString();
                                String start = documentSnapshots.getString("start_time");
                                String end = documentSnapshots.getString("end_time");
                                String breakTime = documentSnapshots.getString("break");

                                WorkShift workShift = new WorkShift(user, date,
                                        start, end, breakTime);
                                ws.add(workShift);
                                workShiftManager  = new WorkShiftManager(ws);
                                System.out.println(workShiftManager.workShiftList.size());
                            }

                            String current_day = dayFormat.format(dateE);
                            dayTime = workShiftManager.getTotalTimeDay(current_day);
                            //breakTime = workShiftManager.getTotalBreakTimeDay(current_day);
                            //totalBreak.setText(breakTime);
                            totalTime.setText(dayTime);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarViewDay)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                db.collection("work_shifts")
                        .whereEqualTo("user", userID)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                        Log.d("TAG", documentSnapshots.getId() + " => " + documentSnapshots.getData());
                                        String user = documentSnapshots.getString("user");
                                        String date = documentSnapshots.get("date").toString();
                                        String start = documentSnapshots.getString("start_time");
                                        String end = documentSnapshots.getString("end_time");
                                        String breakTime = documentSnapshots.getString("break");

                                        WorkShift workShift = new WorkShift(user, date,
                                                start, end, breakTime);
                                        ws.add(workShift);
                                        workShiftManager  = new WorkShiftManager(ws);
                                        System.out.println(workShiftManager.workShiftList.size());
                                    }
                                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                    String dayValue = format1.format(date.getTime());
                                    dayTime = workShiftManager.getTotalTimeDay(dayValue);
                                    //breakTime = workShiftManager.getTotalBreakTimeDay(current_day);
                                    //totalBreak.setText(breakTime);
                                    totalTime.setText(dayTime);
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });

        button_change_day.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), ReportTimeActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}