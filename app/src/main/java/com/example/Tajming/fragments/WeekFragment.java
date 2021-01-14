package com.example.Tajming.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Tajming.R;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class WeekFragment extends Fragment
{
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.WEEK_OF_YEAR, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.WEEK_OF_YEAR, 0);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarViewDay)
                .range(startDate,endDate)
                .datesNumberOnScreen(7)
                .build();

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                WorkShiftManager workShiftManager = getAllWorkShifts();
                LocalDate localDate = LocalDateTime.ofInstant(date.toInstant(), date.getTimeZone().toZoneId()).toLocalDate();
                String totalTime = workShiftManager.getTotalTimeWeek(date.getWeekYear());

            }
        });
        return rootView;
    }
    public WorkShiftManager getAllWorkShifts() {
        WorkShiftManager workShiftManager = new WorkShiftManager();
        CollectionReference workShiftRef = db.collection("work_shifts");
        workShiftRef.whereEqualTo("user", userID);
        Query query = workShiftRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        WorkShift workShift = new WorkShift(document.getString("user"), document.getString("date"),
                                document.getString("start_time"), document.getString("end_time"),
                                document.getString("break"));
                        workShiftManager.addWorkShift(workShift);
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return workShiftManager;
    }
}

